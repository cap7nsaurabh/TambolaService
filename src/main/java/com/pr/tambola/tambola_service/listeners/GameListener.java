package com.pr.tambola.tambola_service.listeners;

import java.util.concurrent.TimeUnit;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.pr.tambola.tambola_service.gameHelper.TambolaGameHandler;
import com.pr.tambola.tambola_service.listeners.ListenerConstants.Events;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationParameters;
import com.pr.tambola.tambola_service.socketHelper.INameSpaceBroadCaster;
import com.pr.tambola.tambola_service.socketHelper.NameSpaceBroadCaster;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameListener {

	SocketIOServer server;
	SocketIONamespace namespace;
	private String path;
	TambolaGameHandler gameHandler;
	private GameCreationParameters parameter;
	Boolean started;
	SocketIOClient host;
	
	
	
	public GameListener(SocketIOServer server,String path, GameCreationParameters parameter){
		this.path = path;
		this.server = server;
		this.parameter = parameter;
		this.started = false;
		initialiseNameSpace(path);
	}
	private void initialiseNameSpace(String path) {
		this.namespace = server.addNamespace(path);
		INameSpaceBroadCaster broadCaster = new NameSpaceBroadCaster(this.namespace);
		gameHandler = new TambolaGameHandler(Integer.parseInt(this.parameter.getTimer()), broadCaster);
		this.namespace.addConnectListener(onConnectListener());
		this.namespace.addDisconnectListener(onDisconnect());
		this.namespace.addEventListener(ListenerConstants.Events.Chat.getLabel(), String.class,onChatEvent());
		this.namespace.addEventListener(ListenerConstants.Events.Game.getLabel(), String.class,onGameChatEvent());
		this.namespace.addEventListener(ListenerConstants.Events.Server.getLabel(), String.class,onServerChatEvent());
	}
	
	private void broadCastMessages(String event,Object data) {
		this.namespace.getBroadcastOperations().sendEvent(event, data);
	}
	
	private DataListener<String> onServerChatEvent() {
		return (client,msg,ackSender)->{
			log.info("client {} sendt a message {} on chat listener in room {}",client,msg,path);
		};
	}
	private ConnectListener onConnectListener() {
		return (client)->{
			if(this.host == null) {
				this.host = client;
			}
			log.info("client {} connected in room: {}",client,this.path);
		};
	}
	
	private DisconnectListener onDisconnect() {
		return (client)->{
			log.info("client {} disconnected from room: {}",client,this.path);
			if(namespace.getAllClients()==null || namespace.getAllClients().isEmpty()) {
				server.removeNamespace(path);
			}
		};
	}
	
	private DataListener<String> onChatEvent(){
		return (client,msg,ackSender)->{
			this.broadCastMessages(ListenerConstants.Events.Chat.getLabel(),msg);
			log.info("client {} sendt a message {} on chat listener in room {}",client,msg,path);
		};
	}
	private void announceGameStart() {
		try {
		String msg = "Game is about to start in: ";
		for(int i = 10;i>0;i--) {
				this.broadCastMessages(Events.Server.getLabel(), msg+i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	private DataListener<String> onGameChatEvent(){
		return (client,msg,ackSender)->{
			if(msg.equals("start") && !started) {
				announceGameStart();
				started=true;
				Thread t1 = new Thread(gameHandler);
				t1.start();
			}
			else {
				client.sendEvent(Events.Server.getLabel(),"Game is already in progress");
			}
			log.info("client {} sendt a message {} on chat listener in room {}",client,msg,path);
		};
	}
}

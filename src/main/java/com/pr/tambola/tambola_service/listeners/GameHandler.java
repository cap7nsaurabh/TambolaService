package com.pr.tambola.tambola_service.listeners;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.pr.tambola.tambola_service.gameHelper.NumberAnnouncer;
import com.pr.tambola.tambola_service.listeners.ListenerConstants.Events;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationParameters;
import com.pr.tambola.tambola_service.model.gameservicemodel.Player;
import com.pr.tambola.tambola_service.socketHelper.INameSpaceBroadCaster;
import com.pr.tambola.tambola_service.socketHelper.NameSpaceBroadCaster;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequestScope
public class GameHandler {
	
	SocketIOServer server;
	SocketIONamespace namespace;
	private String path;
	NumberAnnouncer announcer;
	private GameCreationParameters parameter;
	Boolean started;
	SocketIOClient host;
	List<Player> playerList;
	
	
	public GameHandler(SocketIOServer server){
		this.server = server;
		this.started = false;
		playerList = new ArrayList<>();
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setParameter(GameCreationParameters parameter) {
		this.parameter = parameter;
	}
	
	
	
	public void initialiseNameSpace() {
		log.info("Path:{}",path);
		this.namespace = server.addNamespace(this.path);
		INameSpaceBroadCaster broadCaster = new NameSpaceBroadCaster(this.namespace);
		announcer = new NumberAnnouncer(Integer.parseInt(this.parameter.getTimer()), broadCaster);
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
			playerList.stream().forEach((player)->{
				if(player.getClient().getSessionId()==client.getSessionId()) {
					player.setUserName(msg);
				}
			});
			log.info("client {} sendt a message {} on chat listener in room {}",client,msg,this.path);
		};
	}
	private ConnectListener onConnectListener() {
		return (client)->{
			if(this.host == null) {
				this.host = client;
			}
			Player player = new Player(client);
			playerList.add(player);
			log.info("client {} connected in room: {}",client,this.path);
		};
	}
	
	private DisconnectListener onDisconnect() {
		return (client)->{
			playerList = playerList.stream().filter((player)->client.getSessionId()==player.getClient().getSessionId()).collect(Collectors.toList());
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
	
	private DataListener<String> onGameChatEvent(){
		return (client,msg,ackSender)->{
			if(msg.equals("start") && !this.started) {
				this.started=true;
				Thread t1 = new Thread(announcer);
				t1.start();
			}
			else {
				client.sendEvent(Events.Server.getLabel(),"Game is already in progress");
			}
			log.info("client {} sendt a message {} on chat listener in room {}",client,msg,path);
		};
	}
}

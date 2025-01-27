package com.pr.tambola.tambola_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.corundumstudio.socketio.SocketIOServer;

@Configuration
public class SocketIoConfig {
	
	@Value("${application.socket.host}")
	private String host;
	
	@Value("${application.socket.port}")
	private int port;
	
	
	@Bean
	@Scope("singleton")
	public SocketIOServer initiateSocket() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(host);
		config.setPort(port);
		SocketIOServer server =  new SocketIOServer(config);
		return server;
	}

}

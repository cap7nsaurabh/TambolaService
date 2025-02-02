package com.pr.tambola.tambola_service.model.gameservicemodel;

import com.corundumstudio.socketio.SocketIOClient;

import lombok.Data;

@Data
public class Player {
	SocketIOClient client;
	private String userName;
	//private Ticket ticket;
	public Player(SocketIOClient client) {
		this.client = client;
	}
}

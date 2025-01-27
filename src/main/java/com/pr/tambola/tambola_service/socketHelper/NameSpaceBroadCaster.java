package com.pr.tambola.tambola_service.socketHelper;

import com.corundumstudio.socketio.SocketIONamespace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameSpaceBroadCaster implements INameSpaceBroadCaster {
	
	SocketIONamespace nameSpace;
	
	public void broadCastMessage(String channel,Object message) {
		this.nameSpace.getBroadcastOperations().sendEvent(channel, message);
	}

	
}

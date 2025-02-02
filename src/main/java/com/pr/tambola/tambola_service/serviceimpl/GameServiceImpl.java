package com.pr.tambola.tambola_service.serviceimpl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOServer;
import com.pr.tambola.tambola_service.constants.ServiceConstants;
import com.pr.tambola.tambola_service.constants.ServiceConstants.ErrCodes;
import com.pr.tambola.tambola_service.listeners.GameHandler;
import com.pr.tambola.tambola_service.model.ErrorDetails;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationParameters;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationRequest;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationResponse;
import com.pr.tambola.tambola_service.service.GameService;
import com.pr.tambola.tambola_service.utils.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

	SocketIOServer server;
	
	GameHandler handler;
	public GameServiceImpl(SocketIOServer server,GameHandler handler) {
		this.server = server;
		this.handler = handler;
	}
	
	
	@Override
	public GameCreationResponse createGame(GameCreationRequest request) {
		GameCreationResponse resp = null;
		try {
			String id = createGameId();
			createGameHandler(id,request.getParameter());
			resp = new GameCreationResponse(id);
			resp.setStatus("Success");
		}catch(Exception e) {
			log.error("Exception:",e);
			resp = new GameCreationResponse();
			resp.setStatus("Failure");
			Utility.addErrorToResp(new ErrorDetails(ErrCodes.ServiceErr.getCode(),ErrCodes.ServiceErr.getMsg(),e.getMessage()), resp);
		}
		return resp;
		
	}
	private String createGameId() {
		String id = UUID.randomUUID().toString();
		return id;
	}
	private void createGameHandler(String id,GameCreationParameters parameter) {
		String gameEndpoint = ServiceConstants.GAME_ENDPOINT_PREFIX+id;
		
		handler.setParameter(parameter);
		handler.setPath(gameEndpoint);
		handler.initialiseNameSpace();
	}

}

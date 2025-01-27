package com.pr.tambola.tambola_service.service;

import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationRequest;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationResponse;

public interface GameService {
	public GameCreationResponse createGame(GameCreationRequest request);
}

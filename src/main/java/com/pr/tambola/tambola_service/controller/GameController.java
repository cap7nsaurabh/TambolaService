package com.pr.tambola.tambola_service.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationRequest;
import com.pr.tambola.tambola_service.model.gameservicemodel.GameCreationResponse;
import com.pr.tambola.tambola_service.service.GameService;

@RestController
@RequestMapping(value ="/api")
@CrossOrigin
public class GameController {
	
	GameService gameService;
	
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@PostMapping(value = "/createGame",  headers = "Accept=Application/Json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameCreationResponse> createGame(@RequestBody GameCreationRequest request){
		GameCreationResponse resp = gameService.createGame(request);
		return new ResponseEntity<GameCreationResponse>(resp,HttpStatus.CREATED);
	}
}

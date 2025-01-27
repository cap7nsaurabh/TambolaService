package com.pr.tambola.tambola_service.model.gameservicemodel;

import com.pr.tambola.tambola_service.model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCreationResponse extends Response {
	private String gameId;
}

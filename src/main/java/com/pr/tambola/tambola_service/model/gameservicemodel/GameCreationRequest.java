package com.pr.tambola.tambola_service.model.gameservicemodel;

import com.pr.tambola.tambola_service.model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCreationRequest extends Request{
	private GameCreationParameters parameter;

}

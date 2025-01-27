package com.pr.tambola.tambola_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
	private String errCode;
	private String errMsg;
	private String errDetail;
}

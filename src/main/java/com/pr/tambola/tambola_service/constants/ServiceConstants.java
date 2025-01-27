package com.pr.tambola.tambola_service.constants;

public class ServiceConstants {
	
	public static final String GAME_ENDPOINT_PREFIX = "/game/";

	public static enum ErrCodes{
		ServiceErr("5000","failed to create server");
		

		private String code;
		private String msg;

		ErrCodes(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public String getCode() {
			return this.code;
		}
		public String getMsg() {
			return this.msg;
		}
		
		
	}
}

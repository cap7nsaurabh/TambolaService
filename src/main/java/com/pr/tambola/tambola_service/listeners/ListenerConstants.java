package com.pr.tambola.tambola_service.listeners;

public class ListenerConstants {

	public static enum Events{
		Chat("chat"),
		Game("game"),
		Server("server");

		private String label;

		Events(String label) {
			this.label = label;
		}
		public String getLabel() {
			return this.label;
		}
		
		
		
	}

}

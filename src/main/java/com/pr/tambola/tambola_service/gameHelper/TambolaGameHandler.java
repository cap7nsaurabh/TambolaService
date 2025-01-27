package com.pr.tambola.tambola_service.gameHelper;



import com.pr.tambola.tambola_service.listeners.ListenerConstants.Events;
import com.pr.tambola.tambola_service.socketHelper.INameSpaceBroadCaster;

public class TambolaGameHandler implements Runnable {
	
	int curNum;
	int interval;
	NumberGenerator numberGenerator;
	INameSpaceBroadCaster broadCaster;
	 public TambolaGameHandler(int interval,INameSpaceBroadCaster broadCaster) {
		 this.interval = interval;
		 this.broadCaster = broadCaster;
		 numberGenerator = new NumberGenerator();
	 }
	
	@Override
	public void run() {
		try {
			while(this.curNum!=-1) {
				Thread.sleep(this.interval*100);
				curNum = numberGenerator.generateNumber();
				broadCaster.broadCastMessage(Events.Game.getLabel(),curNum);
			}
			if(curNum==-1) {
				broadCaster.broadCastMessage(Events.Game.getLabel(),"game has ended thank you for playing");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public int curNum() {
		return this.curNum;
	}
	
	

}

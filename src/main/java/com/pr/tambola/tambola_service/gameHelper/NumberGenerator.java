package com.pr.tambola.tambola_service.gameHelper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NumberGenerator {
	
	Set<Integer> numbersAnnounced;
	Random rnd;
	
	public NumberGenerator() {
		 numbersAnnounced = new HashSet<>();
		 rnd = new Random(System.currentTimeMillis());
	}
	
	public int generateNumber(){
		int num = (int)(rnd.nextFloat()*100);
		while(numbersAnnounced.size()<100 && numbersAnnounced.contains(num)) {
			num = (int)(rnd.nextFloat()*100);
		}
		if(numbersAnnounced.size()>=100) {
			return -1;
		}
		numbersAnnounced.add(num);
		return num;
	}

}

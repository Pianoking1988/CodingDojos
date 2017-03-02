package de.heinemann.dojo.quiz.random;

import java.util.Collections;
import java.util.List;

public class RandomImpl implements Random {

	private java.util.Random random = new java.util.Random(); 
	
	@Override
	public void shuffle(List<? extends Object> list) {
		Collections.shuffle(list);		
	}

	@Override
	public int nextInt(int bound) {
		return random.nextInt(bound);		
	}

}

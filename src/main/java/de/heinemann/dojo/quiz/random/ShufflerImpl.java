package de.heinemann.dojo.quiz.random;

import java.util.Collections;
import java.util.List;

public class ShufflerImpl implements Shuffler {

	@Override
	public void shuffle(List<? extends Object> list) {
		Collections.shuffle(list);		
	}

}

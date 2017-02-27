package de.heinemann.dojo.quiz.random;

import java.util.List;

public interface Random {

	public void shuffle(List<? extends Object> list);

	public int nextInt(int bound);
	
}

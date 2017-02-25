package de.heinemann.dojo.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionGenerator {
	
	private int numberOfPossibleAnswers;

	private List<Question> questions = new ArrayList<>();

	public QuestionGenerator(int numberOfPossibleAnswers) {
		this.numberOfPossibleAnswers = numberOfPossibleAnswers;
	}
	
	public List<Question> generateQuestions(List<Country> countries) {
		countries.stream().forEach(country -> {
			questions.add(new Question(country));
		});
		
		return questions;
	}

}

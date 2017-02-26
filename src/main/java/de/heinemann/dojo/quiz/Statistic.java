package de.heinemann.dojo.quiz;

/**
 * Statistic about the number of correct and
 * incorrect answers. 
 */
public class Statistic {

	private int correct = 0;
	private int incorrect = 0;
	
	public void increment(boolean correctAnswer) {
		if (correctAnswer) {
			correct++;
		} else {
			incorrect++;
		}
	}
	
	public int getTotal() {
		return correct + incorrect;
	}
	
	public int getCorrect() {
		return correct;
	}
	
	public int getIncorrect() {
		return incorrect;
	}	
	
	public String getText() {
		return new StringBuilder()
				.append("Von ").append(getSingularOrPluralText(getTotal()))
				.append(" hast du ")
				.append(getSingularOrPluralText(getCorrect())).append(" richtig")
				.append(" und ")
				.append(getSingularOrPluralText(getIncorrect())).append(" falsch")
				.append(" beantwortet.")
				.toString();
	}
	
	private String getSingularOrPluralText(int number) {
		return new StringBuilder(String.valueOf(number))
				.append(" ")
				.append(number == 1 ? "Frage" : "Fragen")
				.toString();
	}
	
}

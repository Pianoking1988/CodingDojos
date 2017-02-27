package de.heinemann.dojo.quiz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.heinemann.dojo.quiz.input.InputReader;
import de.heinemann.dojo.quiz.models.Question;
import de.heinemann.dojo.quiz.models.Statistic;

/**
 * Asks the given questions to the user, evaluates the input and prints statistic
 * about the number of correct & incorrect answers.
 */
public class QuizMaster {
	
	private static final String SEPARATOR = StringUtils.repeat("*", 40);

	private int numberOfQuestions;
	private InputReader inputReader;

	private Statistic statistic = new Statistic();
	
	public QuizMaster(int numberOfQuestions, InputReader inputReader) {
		this.numberOfQuestions = numberOfQuestions;
		this.inputReader = inputReader;
	}

	public void askQuestions(List<Question> questions) {
		System.out.println("So lasset das Spiel beginnen ...");
		System.out.println("");
		for (int i = 0; i < Math.min(numberOfQuestions, questions.size()); i++) {
			askQuestion(questions.get(i));
		}
		System.out.println("Das Spiel ist nun zu Ende. Vielen Dank für deine Teilnahme.");
	}

	public boolean askQuestion(Question question) {
		printQuestion(question);
		
		int index = readIndexFromUserInputUntilItIsValid(question);

		boolean isAnswerCorrect = question.isCorrectAnswerIndex(index); 
		statistic.increment(isAnswerCorrect);
		System.out.println("");
		System.out.println(isAnswerCorrect
				? "Deine Antwort ist richtig."
				: "Deine Antwort ist falsch. Die richtige Antwort wäre " + question.getQuestionAnswerText() + " gewesen.");
		System.out.println(statistic.getText());
		System.out.println("");
		
		return isAnswerCorrect;
	}

	private void printQuestion(Question question) {
		System.out.println(SEPARATOR);
		System.out.println("");
		System.out.println(question.getQuestionText());
		for (int i = 0; i < question.getAllCountries().size(); i++) {
			System.out.println(getCharacterFromIndex(i) + ") " + question.getAnswerTexts().get(i));
		}
		System.out.println("");
	}

	private int readIndexFromUserInputUntilItIsValid(Question question) {
		int index = -1;
		while ((index = getIndexFromUserInput(question)) == -1) {
			System.out.println("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		}
		return index;
	}

	private int getIndexFromUserInput(Question question) {
		String userInput = inputReader.nextLine().trim();
		if (userInput.length() != 1) {
			return -1;
		}
		
		char character = userInput.charAt(0);
		int index = getIndexFromCharacter(character);
		return index >= 0 && index < question.getAllCountries().size() ? index : -1;
	}

	private char getCharacterFromIndex(int index) {
		return (char) (index + 'a');
	}

	private int getIndexFromCharacter(char character) {
		return (int) (character - 'a');
	}

}

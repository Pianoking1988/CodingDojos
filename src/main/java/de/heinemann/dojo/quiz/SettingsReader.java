package de.heinemann.dojo.quiz;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.heinemann.dojo.quiz.input.InputReader;
import de.heinemann.dojo.quiz.models.QuestionType;

/**
 * Asks the user about the settings for the quiz.
 * This includes the number of questions to be asked, 
 * number of possible answers for each question
 * and the quiz question types. 
 */
public class SettingsReader {

	private int numberOfTotalQuestions;
	private InputReader inputReader;

	public SettingsReader(int numberOfTotalQuestions, InputReader inputReader) {
		this.numberOfTotalQuestions = numberOfTotalQuestions;
		this.inputReader = inputReader;
	}

	public int readNumberOfQuestions() {
		System.out.println("Das Quiz kennt insgesamt " + numberOfTotalQuestions + " Fragen. Wie viele Fragen sollen dir gestellt werden?");
		int userInput = readInputFromUserUntilItIsValid(1, numberOfTotalQuestions);
		System.out.println("Danke für deine Antwort. Das Spiel wird dir " + userInput + " Fragen stellen.");
		System.out.println("");

		return userInput;
	}
	
	public int readNumberOfAnswers() {
		System.out.println("Das Quiz kennt insgesamt " + numberOfTotalQuestions + " Fragen. Wieviele Antwortmöglichkeiten soll es geben?");
		int userInput = readInputFromUserUntilItIsValid(2, numberOfTotalQuestions);
		System.out.println("Danke für deine Antwort. Das Spiel wird dir für jede Frage " + userInput + " Antwortmöglichkeiten anzeigen.");
		System.out.println("");

		return userInput;
	}
	
	public List<QuestionType> readQuestionType() {
		QuestionType[] allQuestionTypes = QuestionType.values();
		
		System.out.println("Welchen Spielmodus möchtest du spielen?");
		for (int i = 0; i < allQuestionTypes.length; i++) {
			QuestionType questionType = allQuestionTypes[i];
			System.out.println(questionType.getValue() + ") " + questionType.getDescription());
		}		
		System.out.println((allQuestionTypes.length + 1) + ") Alle Modi zufällig durcheinander spielen");
		
		int userInput = readInputFromUserUntilItIsValid(1, allQuestionTypes.length + 1);
		
		List<QuestionType> result = userInput != allQuestionTypes.length + 1 
				? Arrays.asList(allQuestionTypes[userInput - 1])
				: Arrays.asList(allQuestionTypes);
		
		final List<String> KEYWORDS = result.stream()
				.map(questionType-> questionType.getKeyword())
				.collect(Collectors.toList());
		System.out.println("Danke für deine Antwort. Das Spiel wird dich nach '" + StringUtils.join(KEYWORDS, "' & '") + "' fragen.");
		System.out.println("");
		
		return result;
	}
	
	private int readInputFromUserUntilItIsValid(int inclusiveLowerBound, int inclusiveUpperBound) {
		int result = Integer.MIN_VALUE;
		while ((result = getUserInput(inclusiveLowerBound, inclusiveUpperBound)) == Integer.MIN_VALUE) {
			System.out.println("Deine Antwort habe ich nicht verstanden"
					+ ", da ich eine Zahl zwischen " + inclusiveLowerBound + " und " + inclusiveUpperBound + " benötige."
					+ " Bitte antworte erneut.");
		}
		return result;
	}

	private int getUserInput(int inclusiveLowerBound, int inclusiveUpperBound) {
		try {
			String userInput = inputReader.nextLine().trim();
			int result = Integer.parseInt(userInput);
			return result >= inclusiveLowerBound && result <= inclusiveUpperBound
					? result
					: Integer.MIN_VALUE;
		} catch (Exception exception) {
			return Integer.MIN_VALUE;
		}
	}

}

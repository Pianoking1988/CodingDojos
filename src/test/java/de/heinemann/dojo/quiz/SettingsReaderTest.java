package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.models.QuestionType.ASK_FOR_CAPITAL;
import static de.heinemann.dojo.quiz.models.QuestionType.ASK_FOR_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.mockito.InOrder;

import de.heinemann.dojo.quiz.input.InputReader;
import de.heinemann.dojo.quiz.models.QuestionType;

public class SettingsReaderTest {

	private SettingsReader settingsReader;
	
	private PrintStream out;
	private InputReader inputReader;
	private InOrder inOrder; 
		
	private void init(int numberOfTotalQuestions, String input, String... inputs) {
		out = mock(PrintStream.class);
		System.setOut(out);
		
		inputReader = mock(InputReader.class);
		when(inputReader.nextLine()).thenReturn(input, inputs);
		
		inOrder = inOrder(out, inputReader);
		
		settingsReader = new SettingsReader(numberOfTotalQuestions, inputReader);
	}
		
	/*
	 * Tests for readNumberOfQuestions
	 ******************************************/

	private void readAndAssertNumberOfQuestions(int expected, int reAsks, String input, String...inputs) {
		init(4, input, inputs);
		
		final String REASK_TEXT = "Deine Antwort habe ich nicht verstanden"
				+ ", da ich eine Zahl zwischen 1 und 4 benötige."
				+ " Bitte antworte erneut.";
		
		int actual = settingsReader.readNumberOfQuestions();
		
		assertEquals("return value", expected, actual);
		assertSystemOut("Das Quiz kennt insgesamt 4 Fragen. Wie viele Fragen sollen dir gestellt werden?");
		assertInputReader();
		
		if (reAsks > 0) {
			for (int i = 0; i < reAsks; i++) {
				assertSystemOut(REASK_TEXT);
				assertInputReader();
			} 
		} else {
			inOrder.verify(out, never()).println(REASK_TEXT);
			inOrder.verify(inputReader, never()).nextLine();
		}
		
		assertSystemOut("Danke für deine Antwort. Das Spiel wird dir " + expected + " Fragen stellen.", "");
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	public void readNumberOfQuestionsWithCorrectInput() {
		readAndAssertNumberOfQuestions(2, 0, "2");
	}

	@Test
	public void readNumberOfQuestionsWithCorrectInputButWithLeadingAndTrailingSpaces() {
		readAndAssertNumberOfQuestions(2, 0, "  2  ");
	}

	@Test
	public void readNumberOfQuestionsWithTooLowNumberAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfQuestions(1, 1, "0", "1");
	}
	
	@Test
	public void readNumberOfQuestionsWithTooHighNumberTwiceAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfQuestions(4, 2, "5", "6", "4");
	}
	
	@Test
	public void readNumberOfQuestionsWithEmptyStringAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfQuestions(2, 1, "", "2");
	}
	
	@Test
	public void readNumberOfQuestionsWithLetterAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfQuestions(4, 1, "a", "4");
	}	
	
	/*
	 * Tests for readNumberOfAnswers
	 ******************************************/

	private void readAndAssertNumberOfAnswers(int numberOfTotalQuestions, int maxUserInput, int expected, int reAsks, String input, String...inputs) {
		init(numberOfTotalQuestions, input, inputs);
		
		final String REASK_TEXT = "Deine Antwort habe ich nicht verstanden"
				+ ", da ich eine Zahl zwischen 2 und " + maxUserInput + " benötige."
				+ " Bitte antworte erneut.";
		
		int actual = settingsReader.readNumberOfAnswers();
		
		assertEquals("return value", expected, actual);
		assertSystemOut("Das Quiz kennt insgesamt " + numberOfTotalQuestions + " Fragen. Wieviele Antwortmöglichkeiten soll es geben?"
				+ " Wähle zwischen 2 und " + maxUserInput + ".");
		assertInputReader();
		
		if (reAsks > 0) {
			for (int i = 0; i < reAsks; i++) {
				assertSystemOut(REASK_TEXT);
				assertInputReader();
			} 
		} else {
			inOrder.verify(out, never()).println(REASK_TEXT);
			inOrder.verify(inputReader, never()).nextLine();
		}
		
		assertSystemOut("Danke für deine Antwort. Das Spiel wird dir für jede Frage " + expected + " Antwortmöglichkeiten anzeigen.", "");
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	public void readNumberOfAnswersWithCorrectInput() {
		readAndAssertNumberOfAnswers(4, 4, 2, 0, "2");
	}

	@Test
	public void readNumberOfAnswersWithCorrectInputButWithLeadingAndTrailingSpaces() {
		readAndAssertNumberOfAnswers(4, 4, 3, 0, " 3 ");
	}

	@Test
	public void readNumberOfAnswersWithTooLowNumberAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfAnswers(4, 4, 2, 1, " 1 ", "2");
	}

	@Test
	public void readNumberOfAnswersWithTooHighNumberTwiceAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfAnswers(4, 4, 4, 2, "5", "6", "4");
	}

	@Test
	public void readNumberOfAnswersWithEmptyStringAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfAnswers(4, 4, 2, 1, "", "2");
	}

	@Test
	public void readNumberOfAnswersWithLetterAndAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfAnswers(4, 4, 4, 1, "a", "4");
	}
	
	@Test
	public void readNumberOfAnswersWithNumberGreaterTwentyAnswerCorrectlyAfterwards() {
		readAndAssertNumberOfAnswers(21, 20, 20, 1, "21", "20");
	}
	
	/*
	 * Tests for readQuestionType
	 ******************************************/
	             
	private void readAndAssertQuestionType(List<QuestionType> expected, int reAsks, String input, String...inputs) {
		init(4, input, inputs);
		
		final String REASK_TEXT = "Deine Antwort habe ich nicht verstanden"
				+ ", da ich eine Zahl zwischen 1 und 3 benötige."
				+ " Bitte antworte erneut.";
		
		List<QuestionType> actual = settingsReader.readQuestionType();
		
		assertEquals("return value", expected, actual);
		assertSystemOut("Welchen Spielmodus möchtest du spielen?",
				"1) " + ASK_FOR_CAPITAL.getDescription(),
				"2) " + ASK_FOR_NAME.getDescription(),
				"3) Alle Modi zufällig durcheinander spielen");
		assertInputReader();
		
		if (reAsks > 0) {
			for (int i = 0; i < reAsks; i++) {
				assertSystemOut(REASK_TEXT);
				assertInputReader();
			} 
		} else {
			inOrder.verify(out, never()).println(REASK_TEXT);
			inOrder.verify(inputReader, never()).nextLine();
		}
		
		final List<String> KEYWORDS = expected.stream()
				.map(questionType-> questionType.getKeyword())
				.collect(Collectors.toList());
		assertSystemOut("Danke für deine Antwort. Das Spiel wird dich nach '" + StringUtils.join(KEYWORDS, "' & '") + "' fragen.", "");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void readQuestionTypeWithCorrectInput() {
		readAndAssertQuestionType(Arrays.asList(ASK_FOR_CAPITAL), 0, "1");
	}

	@Test
	public void readQuestionTypeWithCorrectInputButWithLeadingAndTrailingSpaces() {
		readAndAssertQuestionType(Arrays.asList(ASK_FOR_NAME), 0, " 2 ");
	}
	
	@Test
	public void readQuestionTypeWithTooLowNumberAndAnswerCorrectlyAfterwards() {
		readAndAssertQuestionType(Arrays.asList(ASK_FOR_CAPITAL, ASK_FOR_NAME), 1, " 0 ", "3");
	}


	@Test
	public void readQuestionTypeWithTooHighNumberTwiceAndAnswerCorrectlyAfterwards() {
		readAndAssertQuestionType(Arrays.asList(ASK_FOR_CAPITAL, ASK_FOR_NAME), 2, " 4 ", "5", "3");
	}

	@Test
	public void readQuestionTypeWithEmptyStringAndAnswerCorrectlyAfterwards() {
		readAndAssertQuestionType(Arrays.asList(ASK_FOR_CAPITAL), 1, "", "1");
	}

	@Test
	public void readQuestionTypeWithLetterAndAnswerCorrectlyAfterwards() {
		readAndAssertQuestionType(Arrays.asList(ASK_FOR_NAME), 1, "", "2");
	}
	
	private void assertSystemOut(String... lines) {
		for (String line : lines) {
			inOrder.verify(out, times(1)).println(line);
		}
	}

	private void assertInputReader() {
		inOrder.verify(inputReader, times(1)).nextLine();
	}

}

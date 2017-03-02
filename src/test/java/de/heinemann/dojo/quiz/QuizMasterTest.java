package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.TestUtils.FRANCE;
import static de.heinemann.dojo.quiz.TestUtils.GERMANY;
import static de.heinemann.dojo.quiz.TestUtils.ITALY;
import static de.heinemann.dojo.quiz.TestUtils.SEPARATOR;
import static de.heinemann.dojo.quiz.TestUtils.SPAIN;
import static de.heinemann.dojo.quiz.TestUtils.question;
import static de.heinemann.dojo.quiz.TestUtils.questions;
import static de.heinemann.dojo.quiz.model.QuestionType.ASK_FOR_CAPITAL;
import static de.heinemann.dojo.quiz.model.QuestionType.ASK_FOR_NAME;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;

import de.heinemann.dojo.quiz.input.InputReader;
import de.heinemann.dojo.quiz.model.Question;

public class QuizMasterTest {

	private final Question QUESTION_FOR_CAPITAL = question(ASK_FOR_CAPITAL, GERMANY, FRANCE, ITALY);
	private final Question QUESTION_FOR_NAME = question(ASK_FOR_NAME, GERMANY, FRANCE, ITALY);
	
	private final List<Question> QUESTIONS = questions(
			question(ASK_FOR_CAPITAL, GERMANY, FRANCE, ITALY),
			question(ASK_FOR_CAPITAL, FRANCE, GERMANY, ITALY),
			question(ASK_FOR_CAPITAL, ITALY, GERMANY, FRANCE),
			question(ASK_FOR_CAPITAL, SPAIN, GERMANY, FRANCE)
	);
	
	private QuizMaster quizMaster;
	
	private PrintStream out;
	private InputReader inputReader;
	private InOrder inOrder; 
		
	private void init(String input, String... inputs) {
		out = mock(PrintStream.class);
		System.setOut(out);
		
		inputReader = mock(InputReader.class);
		when(inputReader.nextLine()).thenReturn(input, inputs);
		
		inOrder = inOrder(out, inputReader);
		
		quizMaster = new QuizMaster(3, inputReader);
	}
	
	/*
	 * Tests for single questions for capital 
	 ******************************************/
	
	private void askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(int reAsks, String input, String... inputs) {
		init(input, inputs);

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION_FOR_CAPITAL));

		assertSystemOut(SEPARATOR, "", "Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom",
				"");
		assertInputReader();
		
		if (reAsks > 0) {
			for (int i = 0; i < reAsks; i++) {
				assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
				assertInputReader();
			} 
		} else {
			inOrder.verify(out, never()).println("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
			inOrder.verify(inputReader, never()).nextLine();
		}
		
		assertSystemOut("", "Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.", "");
		inOrder.verifyNoMoreInteractions();
	}
		
	@Test
	public void askSingleQuestionForCapitalAndAnswerIncorrectly() {
		init("b");

		assertFalse("askQuestion should return false", quizMaster.askQuestion(QUESTION_FOR_CAPITAL));
		
		assertSystemOut(SEPARATOR, "", "Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom",
				"");
		assertInputReader();
		assertSystemOut("", "Deine Antwort ist falsch. Die richtige Antwort wäre Berlin gewesen.",
				"Von 1 Frage hast du 0 Fragen richtig und 1 Frage falsch beantwortet.", "");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionForCapitalAndAnswerCorrectly() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(0, "a");
	}
	
	@Test
	public void askSingleQuestionForCapitalAndAnswerCorrectlyButWithLeadingAndTrailingSpaces() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(0, " a ");
	}

	@Test
	public void askSingleQuestionForCapitalAndWithEmptyStringAndAnswerCorrectlyAfterwards() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(1, "", "a");
	}

	@Test
	public void askSingleQuestionForCapitalAndAnswerWithNumberAndAnswerCorrectlyAfterwards() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(1, "9", "a");
	}

	@Test
	public void askSingleQuestionForCapitalAndAnswerWithIllegalLetterAndAnswerCorrectlyAfterwards() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(1, "d", "a");
	}

	@Test
	public void askSingleQuestionForCapitalAndAnswerNotWithSingleLetterAndAnswerCorrectlyAfterwards() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(1, "ab", "a");
	}

	@Test
	public void askSingleQuestionForCapitalAndAnswerTwoTimesWithNumberAndAnswerCorrectlyAfterwards() {
		askAndAssertSingleQuestionForCapitalAndAnswerCorrectly(2, "9", "1", "a");
	}
	
	/*
	 * Tests for single questions for name 
	 ******************************************/	
	
	private void askAndAssertSingleQuestionForNameAndAnswerCorrectly(int reAsks, String input, String... inputs) {
		init(input, inputs);

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION_FOR_NAME));

		assertSystemOut(SEPARATOR, "", "Zu welchem Land gehört die Hauptstadt Berlin?",
				"a) Deutschland",
				"b) Frankreich",
				"c) Italien",
				"");
		assertInputReader();
		
		if (reAsks > 0) {
			for (int i = 0; i < reAsks; i++) {
				assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
				assertInputReader();
			} 
		} else {
			inOrder.verify(out, never()).println("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
			inOrder.verify(inputReader, never()).nextLine();
		}
		
		assertSystemOut("", "Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.", "");
		inOrder.verifyNoMoreInteractions();
	}		
	
	@Test
	public void askSingleQuestionForNameAndAnswerIncorrectly() {
		init("b");

		assertFalse("askQuestion should return false", quizMaster.askQuestion(QUESTION_FOR_NAME));
		
		assertSystemOut(SEPARATOR, "", "Zu welchem Land gehört die Hauptstadt Berlin?",
				"a) Deutschland",
				"b) Frankreich",
				"c) Italien",
				"");
		assertInputReader();
		assertSystemOut("", "Deine Antwort ist falsch. Die richtige Antwort wäre Deutschland gewesen.",
				"Von 1 Frage hast du 0 Fragen richtig und 1 Frage falsch beantwortet.", "");
		inOrder.verifyNoMoreInteractions();
	}	
	
	@Test
	public void askSingleQuestionForNameAndAnswerCorrectly() {
		askAndAssertSingleQuestionForNameAndAnswerCorrectly(0, "a");
	}
	
	/*
	 * Tests for questions 
	 ******************************************/
	
	private void askAndAssertQuestions(List<Question> questions, int numberOfQuestions, String input, String... inputs) {
		init(input, inputs);
		
		quizMaster.askQuestions(questions);
		
		assertSystemOut("So lasset das Spiel beginnen ...");
		verify(out, times(numberOfQuestions)).println(Matchers.startsWith("Wie lautet die Hauptstadt von"));		
		verify(out, times(1)).println("Das Spiel ist nun zu Ende. Vielen Dank für deine Teilnahme.");		
	}

	@Test
	public void askQuestionsWithOneItemQuestionList() {
		askAndAssertQuestions(QUESTIONS.subList(0, 1), 1, "a");
	}

	@Test
	public void askQuestionsWithTwoItemQuestionList() {
		askAndAssertQuestions(QUESTIONS.subList(0, 2), 2, "a", "b");
	}

	@Test
	public void askQuestionsWithThreeItemQuestionList() {
		askAndAssertQuestions(QUESTIONS.subList(0, 3), 3, "a", "b", "c");
	}

	@Test
	public void askQuestionsWithFourItemQuestionList() {
		askAndAssertQuestions(QUESTIONS, 3, "a", "b", "c");
	}

	private void assertInputReader() {
		inOrder.verify(inputReader, times(1)).nextLine();
	}

	private void assertSystemOut(String... lines) {
		for (String line : lines) {
			inOrder.verify(out, times(1)).println(line);
		}
	}
	
}
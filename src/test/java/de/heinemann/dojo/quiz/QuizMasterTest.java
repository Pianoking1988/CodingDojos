package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.TestUtils.FRANCE;
import static de.heinemann.dojo.quiz.TestUtils.GERMANY;
import static de.heinemann.dojo.quiz.TestUtils.ITALY;
import static de.heinemann.dojo.quiz.TestUtils.SPAIN;
import static de.heinemann.dojo.quiz.TestUtils.question;
import static de.heinemann.dojo.quiz.TestUtils.questions;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;

public class QuizMasterTest {

	private final Question QUESTION = question(GERMANY, FRANCE, ITALY);
	private final List<Question> QUESTIONS = questions(
			question(GERMANY, FRANCE, ITALY),
			question(FRANCE, GERMANY, ITALY),
			question(ITALY, GERMANY, FRANCE),
			question(SPAIN, GERMANY, FRANCE)
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
	
	@Test
	public void askSingleQuestionAndAnswerCorrectly() {
		init("a");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));

		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndAnswerIncorrectly() {
		init("b");

		assertFalse("askQuestion should return false", quizMaster.askQuestion(QUESTION));
		
		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort ist falsch. Die richtige Antwort wäre Berlin gewesen.",
				"Von 1 Frage hast du 0 Fragen richtig und 1 Frage falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndAnswerCorrectlyButWithLeadingAndTrailingSpaces() {
		init(" a ");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));

		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndWithEmptyStringAndAnswerCorrectlyAfterwards() {
		init("", "a");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));
		
		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndAnswerWithNumberAndAnswerCorrectlyAfterwards() {
		init("9", "a");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));
		
		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndAnswerWithIllegalLetterAndAnswerCorrectlyAfterwards() {
		init("d", "a");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));
		
		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndAnswerNotWithSingleLetterAndAnswerCorrectlyAfterwards() {
		init("ab", "a");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));
		
		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askSingleQuestionAndAnswerTwoTimesWithNumberAndAnswerCorrectlyAfterwards() {
		init("9", "1", "a");

		assertTrue("askQuestion should return true", quizMaster.askQuestion(QUESTION));
		
		assertSystemOut("Wie lautet die Hauptstadt von Deutschland?",
				"a) Berlin",
				"b) Paris",
				"c) Rom");
		assertInputReader();
		assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		assertInputReader();
		assertSystemOut("Deine Antwort habe ich nicht verstanden. Bitte antworte erneut.");
		assertInputReader();
		assertSystemOut("Deine Antwort ist richtig.",
				"Von 1 Frage hast du 1 Frage richtig und 0 Fragen falsch beantwortet.");
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void askFourQuestions() {
		init("a", "b", "c");
		
		quizMaster.askQuestions(QUESTIONS);
		
		assertSystemOut("So lasset das Spiel beginnen ...");
		verify(out, times(3)).println(Matchers.startsWith("Wie lautet die Hauptstadt von"));		
		verify(out, times(1)).println("Das Spiel ist nun zu Ende. Vielen Dank für deine Teilnahme");
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

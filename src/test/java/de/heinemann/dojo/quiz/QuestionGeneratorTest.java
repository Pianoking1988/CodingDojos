package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.TestUtils.FRANCE;
import static de.heinemann.dojo.quiz.TestUtils.GERMANY;
import static de.heinemann.dojo.quiz.TestUtils.ITALY;
import static de.heinemann.dojo.quiz.TestUtils.SPAIN;
import static de.heinemann.dojo.quiz.TestUtils.countries;
import static de.heinemann.dojo.quiz.TestUtils.question;
import static de.heinemann.dojo.quiz.model.QuestionType.ASK_FOR_CAPITAL;
import static de.heinemann.dojo.quiz.model.QuestionType.ASK_FOR_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import de.heinemann.dojo.quiz.model.Country;
import de.heinemann.dojo.quiz.model.Question;
import de.heinemann.dojo.quiz.model.QuestionType;
import de.heinemann.dojo.quiz.random.Random;

public class QuestionGeneratorTest {
	
	private Random random;
	private QuestionGenerator questionGenerator;
	
	private void init(List<QuestionType> questionTypes, Integer randomInt, Integer... randomInts) {
		random = Mockito.mock(Random.class);
		when(random.nextInt(questionTypes.size())).thenReturn(randomInt, randomInts);
		
		questionGenerator = new QuestionGenerator(3, questionTypes, random);		
	}
	
	@Test
	public void emptyCountryListShouldReturnEmptyQuestionList() {
		init(Arrays.asList(ASK_FOR_CAPITAL), 0);
		
		List<Country> countries = countries();
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertNotNull(questions);
		assertEquals("Number of questions", 0, questions.size());
		verify(random, times(1)).shuffle(Matchers.anyList());
	}
	
	@Test
	public void oneItemCountryListShouldReturnPseudoShuffledQuestionList() {
		init(Arrays.asList(ASK_FOR_NAME), 0);
		List<Country> countries = countries(GERMANY);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 1, questions.size());
		assertQuestion(question(ASK_FOR_NAME, GERMANY), questions.get(0));
		verify(random, times(3)).shuffle(Matchers.anyList());
	}

	@Test
	public void twoItemCountryListShouldReturnPseudoShuffledQuestionList() {
		init(Arrays.asList(ASK_FOR_CAPITAL), 0);
		List<Country> countries = countries(GERMANY, FRANCE);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 2, questions.size());
		assertQuestion(question(ASK_FOR_CAPITAL, GERMANY, FRANCE), questions.get(0));
		assertQuestion(question(ASK_FOR_CAPITAL, FRANCE, GERMANY), questions.get(1));
		verify(random, times(5)).shuffle(Matchers.anyList());
	}

	@Test
	public void threeItemCountryListShouldReturnPseudoShuffledQuestionList() {
		init(Arrays.asList(ASK_FOR_CAPITAL, ASK_FOR_NAME), 1, -1, 2);
		List<Country> countries = countries(GERMANY, FRANCE, ITALY);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 3, questions.size());
		assertQuestion(question(ASK_FOR_NAME, GERMANY, FRANCE, ITALY), questions.get(0));
		assertQuestion(question(ASK_FOR_CAPITAL, FRANCE, GERMANY, ITALY), questions.get(1));
		assertQuestion(question(ASK_FOR_CAPITAL, ITALY, GERMANY, FRANCE), questions.get(2));
		verify(random, times(7)).shuffle(Matchers.anyList());
	}

	@Test
	public void fourItemCountryListShouldReturnPseudoShuffledQuestionList() {
		init(Arrays.asList(ASK_FOR_NAME, ASK_FOR_CAPITAL), 0, 1, 1, 0);
		List<Country> countries = countries(GERMANY, FRANCE, ITALY, SPAIN);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 4, questions.size());
		assertQuestion(question(ASK_FOR_NAME, GERMANY, FRANCE, ITALY), questions.get(0));
		assertQuestion(question(ASK_FOR_CAPITAL, FRANCE, GERMANY, ITALY), questions.get(1));
		assertQuestion(question(ASK_FOR_CAPITAL, ITALY, GERMANY, FRANCE), questions.get(2));
		assertQuestion(question(ASK_FOR_NAME, SPAIN, GERMANY, FRANCE), questions.get(3));
		verify(random, times(9)).shuffle(Matchers.anyList());
	}

	private void assertQuestion(Question expected, Question actual) {
		assertNotNull(actual);
		assertCountry(expected.getCountry(), actual.getCountry());
		assertEquals("Question type", expected.getQuestionType(), actual.getQuestionType());
		assertEquals("Number of allCountries in question", 
				expected.getAllCountries().size(), actual.getAllCountries().size());
		for (int i = 0; i < expected.getAllCountries().size(); i++) {
			assertCountry(expected.getAllCountries().get(0), actual.getAllCountries().get(0));
		}
	}
	
	private void assertCountry(Country expected, Country actual) {
		assertNotNull(actual);
		assertEquals("Name of country", expected.getName(), actual.getName());
		assertEquals("Capital of country", expected.getCapital(), actual.getCapital());
	}
	
}

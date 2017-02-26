package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.TestUtils.FRANCE;
import static de.heinemann.dojo.quiz.TestUtils.GERMANY;
import static de.heinemann.dojo.quiz.TestUtils.ITALY;
import static de.heinemann.dojo.quiz.TestUtils.SPAIN;
import static de.heinemann.dojo.quiz.TestUtils.countries;
import static de.heinemann.dojo.quiz.TestUtils.question;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class QuestionGeneratorTest {
	
	private Shuffler shuffler = Mockito.mock(Shuffler.class);
	private QuestionGenerator questionGenerator = new QuestionGenerator(3, shuffler);
	
	@Test
	public void emptyCountryListShouldReturnEmptyQuestionList() {
		List<Country> countries = countries();
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertNotNull(questions);
		assertEquals("Number of questions", 0, questions.size());
		verify(shuffler, times(1)).shuffle(Matchers.anyList());
	}
	
	@Test
	public void oneItemCountryListShouldReturnPseudoShuffledQuestionList() {
		List<Country> countries = countries(GERMANY);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 1, questions.size());
		assertQuestion(question(GERMANY), questions.get(0));
		verify(shuffler, times(3)).shuffle(Matchers.anyList());
	}

	@Test
	public void twoItemCountryListShouldReturnPseudoShuffledQuestionList() {
		List<Country> countries = countries(GERMANY, FRANCE);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 2, questions.size());
		assertQuestion(question(GERMANY, FRANCE), questions.get(0));
		assertQuestion(question(FRANCE, GERMANY), questions.get(1));
		verify(shuffler, times(5)).shuffle(Matchers.anyList());
	}

	@Test
	public void threeItemCountryListShouldReturnPseudoShuffledQuestionList() {
		List<Country> countries = countries(GERMANY, FRANCE, ITALY);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 3, questions.size());
		assertQuestion(question(GERMANY, FRANCE, ITALY), questions.get(0));
		assertQuestion(question(FRANCE, GERMANY, ITALY), questions.get(1));
		assertQuestion(question(ITALY, GERMANY, FRANCE), questions.get(2));
		verify(shuffler, times(7)).shuffle(Matchers.anyList());
	}

	@Test
	public void fourItemCountryListShouldReturnPseudoShuffledQuestionList() {
		List<Country> countries = countries(GERMANY, FRANCE, ITALY, SPAIN);
		List<Question> questions = questionGenerator.generateQuestions(countries);

		assertEquals("Number of questions", 4, questions.size());
		assertQuestion(question(GERMANY, FRANCE, ITALY), questions.get(0));
		assertQuestion(question(FRANCE, GERMANY, ITALY), questions.get(1));
		assertQuestion(question(ITALY, GERMANY, FRANCE), questions.get(2));
		assertQuestion(question(SPAIN, GERMANY, FRANCE), questions.get(3));
		verify(shuffler, times(9)).shuffle(Matchers.anyList());
	}

	private void assertQuestion(Question expected, Question actual) {
		assertNotNull(actual);
		assertCountry(expected.getCountry(), actual.getCountry());
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

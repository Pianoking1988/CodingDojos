package de.heinemann.dojo.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class QuestionGeneratorTest {

	private final Country GERMANY = new Country("Deutschland", "Berlin");
	
	private QuestionGenerator questionGenerator = new QuestionGenerator(3);

	@Test
	public void emptyCountryListShouldReturnEmptyQuestionList() {
		List<Country> countries = countries();
		List<Question> questions = questionGenerator.generateQuestions(countries);
		assertNotNull(questions);
		assertEquals("Number of questions", 0, questions.size());
	}
	
	@Test
	public void oneItemCountryListShouldReturnOneItemQuestionList() {
		List<Country> countries = countries(GERMANY);
		List<Question> questions = questionGenerator.generateQuestions(countries);
		assertEquals("Number of questions", 1, questions.size());
		assertQuestion(question(GERMANY, GERMANY), questions.get(0));
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
		assertEquals("Capital of country", expected.getCapitol(), actual.getCapitol());
	}
	
	private Question question(Country country, Country... allCountries) {
		List<Country> countries = new ArrayList<>();
		for (Country allCountry : allCountries) {
			countries.add(allCountry);
		}
				
		Question result = new Question(country);
		result.setAllCountries(countries);
		return result;
	}

	private List<Country> countries(Country... countries) {
		List<Country> result = new ArrayList<>();
		for (Country country : countries) {
			result.add(country);
		}
		return result;
	}
	
}

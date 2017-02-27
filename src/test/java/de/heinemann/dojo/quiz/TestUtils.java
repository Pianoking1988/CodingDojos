package de.heinemann.dojo.quiz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.heinemann.dojo.quiz.models.Country;
import de.heinemann.dojo.quiz.models.Question;
import de.heinemann.dojo.quiz.models.QuestionType;

/**
 * Class containing objects for testing and methods for building test objects.
 * 
 * Results of methods containing countries will not contain the original country, 
 * but a copy of it to avoid errors if the original country object is used in test for verification.
 * For example if the method to be tested changes the country name accidentally
 * and the test compares GERMANY.name equals country.name which will mask the error. 
 */
public class TestUtils {

	public static final String SEPARATOR = StringUtils.repeat("*", 40);

	public final static Country GERMANY = new Country("Deutschland", "Berlin");
	public final static Country FRANCE = new Country("Frankreich", "Paris");
	public final static Country ITALY = new Country("Italien", "Rom");
	public final static Country SPAIN = new Country("Spanien", "Madrid");

	public static List<Question> questions(Question... questions) {
		List<Question> result = new ArrayList<Question>();
		for (Question question : questions) {
			result.add(question);
		}
		return result;
	}
	
	public static Question question(QuestionType questionType, Country... allCountries) {
		List<Country> countries = countries(allCountries);
		Question result = new Question(countries.get(0));
		result.setAllCountries(countries);
		result.setQuestionType(questionType);
		return result;
	}

	public static List<Country> countries(Country... countries) {
		List<Country> result = new ArrayList<>();
		for (Country country : countries) {
			result.add(country(country));
		}
		return result;
	}
	
	public static Country country(Country country) {
		return new Country(country.getName(), country.getCapital());
	}

}

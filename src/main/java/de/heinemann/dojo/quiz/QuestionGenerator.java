package de.heinemann.dojo.quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a shuffled list of questions based on the given shuffler.
 * The number of possible answers of each question can be specified
 * by the parameter {@link #numberOfPossibleAnswers}.
 */
public class QuestionGenerator {
	
	private int numberOfPossibleAnswers;
	private Shuffler shuffler;

	private List<Question> questions = new ArrayList<>();

	public QuestionGenerator(int numberOfPossibleAnswers, Shuffler shuffler) {
		this.numberOfPossibleAnswers = numberOfPossibleAnswers;
		this.shuffler = shuffler;
	}
	
	/**
	 * Returns a list of questions based on the given countries.
	 * Each question will contain one country and a list of all possible distinct country
	 * answers trimmed to {@link #numberOfPossibleAnswers} and shuffled including
	 * the correct country.
	 * The list of questions will be shuffled.
	 * 
	 * @param countries list of all countries
	 */
	public List<Question> generateQuestions(List<Country> countries) {
		countries.stream().forEach(country -> {
			Question question = new Question(country);
			question.setAllCountries(buildAllCountriesList(countries, country));
			questions.add(question);			
		});
		
		shuffler.shuffle(questions);
		return questions;
	}

	/**
	 * Returns a shuffled list of distinct countries trimmed to size of {@link #numberOfPossibleAnswers} 
	 * and ensures that the given country is contained in the return list.
	 * 
	 * @param countries list of all countries
	 * @param country country that has to be part of the resulting list
	 * @return 
	 */
	private List<Country> buildAllCountriesList(List<Country> originalCountries, Country country) {
		List<Country> countries = new ArrayList<>(originalCountries);
		countries.remove(country);
		shuffler.shuffle(countries);
		countries = getSubList(countries, 0, numberOfPossibleAnswers - 1);
		countries.add(0, country);
		shuffler.shuffle(countries);
		return countries;
	}

	/**
	 * Returns a sub list of the given list based on the given indices.
	 * If the toIndex parameter is out of Bounds, the input list will be returned.
	 * 
	 * @param fromIndex low endpoint (inclusive) of the subList
	 * @param toIndex high endpoint (exclusive) of the subList
	 * @return
	 */
	private List<Country> getSubList(List<Country> countries, int fromIndex, int toIndex) {
		return countries.size() >= numberOfPossibleAnswers - 1 
				? countries.subList(0, numberOfPossibleAnswers - 1)
				: countries;
	}
}
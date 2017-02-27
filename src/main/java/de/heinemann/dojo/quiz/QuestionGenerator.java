package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.models.QuestionType.ASK_FOR_CAPITAL;

import java.util.ArrayList;
import java.util.List;

import de.heinemann.dojo.quiz.models.Country;
import de.heinemann.dojo.quiz.models.Question;
import de.heinemann.dojo.quiz.models.QuestionType;
import de.heinemann.dojo.quiz.random.Random;

/**
 * Creates a shuffled list of questions based on the given shuffler.
 * The number of possible answers of each question can be specified
 * by the parameter {@link #numberOfPossibleAnswers}.
 * The type of each question will be randomly picked from the given array
 * of question types.
 */
public class QuestionGenerator {
	
	private int numberOfPossibleAnswers;
	private List<QuestionType> questionTypes;
	private Random random;

	private List<Question> questions = new ArrayList<>();

	public QuestionGenerator(int numberOfPossibleAnswers, List<QuestionType> questionTypes, Random random) {
		this.numberOfPossibleAnswers = numberOfPossibleAnswers;
		this.questionTypes = questionTypes;
		this.random = random;
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
			question.setQuestionType(getRandomQuestionType());
			questions.add(question);			
		});
		
		random.shuffle(questions);
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
		random.shuffle(countries);
		countries = getSubList(countries, 0, numberOfPossibleAnswers - 1);
		countries.add(0, country);
		random.shuffle(countries);
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

	/**
	 * Returns a random question type out of {@link #questionTypes}.
	 */
	private QuestionType getRandomQuestionType() {
		int randomIndex = random.nextInt(questionTypes.size());
		return randomIndex >= 0 && randomIndex < questionTypes.size()
				? questionTypes.get(randomIndex)
				: ASK_FOR_CAPITAL;
	}

}
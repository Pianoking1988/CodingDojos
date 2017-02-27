package de.heinemann.dojo.quiz.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains the country that is used for the question and the correct answer.
 * Contains all countries that are used to display the possible answers which has to
 * include the correct answer country.
 * 
 * The variable {@link #capitalQuestion} determines the question type, for example:
 * country -> capital question or a capital -> country question.
 * 
 * The variables {@link #country} and {@link #allCountries} are stored as {@link Country}'s 
 * to make it simple to deliver the capital or country name based on the question type. 
 */
public class Question {

	private Country country;
	private List<Country> allCountries = new ArrayList<>();
	private QuestionType questionType = QuestionType.ASK_FOR_CAPITAL;
	
	public Question(Country country) {
		this.country = country;
	}

	public String getQuestionText() {
		return questionType == QuestionType.ASK_FOR_CAPITAL
				? "Wie lautet die Hauptstadt von " + country.getName() + "?"
				: "Zu welchem Land gehört die Hauptstadt " + country.getCapital() + "?";
	}
	
	public String getQuestionAnswerText() {
		return questionType == QuestionType.ASK_FOR_CAPITAL
				? country.getCapital()
				: country.getName();
	}
	
	public List<String> getAnswerTexts() {
		return allCountries.stream()
				.map(country ->
						questionType == QuestionType.ASK_FOR_CAPITAL
								? country.getCapital()
								: country.getName())
				.collect(Collectors.toList());
	}
	
	public boolean isCorrectAnswerIndex(int index) {
		return index >= 0 && index < allCountries.size()
				&& allCountries.get(index) == country;
	}

	public Country getCountry() {
		return country;
	}

	public void setAllCountries(List<Country> allCountries) {
		this.allCountries = allCountries;	
	}

	public List<Country> getAllCountries() {
		return allCountries;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

}

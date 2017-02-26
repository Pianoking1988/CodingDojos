package de.heinemann.dojo.quiz.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Question {

	private Country country;
	private List<Country> allCountries = new ArrayList<>();
	
	public Question(Country country) {
		this.country = country;
	}

	public String getQuestionText() {
		return "Wie lautet die Hauptstadt von " + country.getName() + "?";
	}
	
	public String getQuestionAnswerText() {
		return country.getCapital();
	}
	
	public List<String> getAnswerTexts() {
		return allCountries.stream().map(country -> country.getCapital())
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

}

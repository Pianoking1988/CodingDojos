package de.heinemann.dojo.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {

	private Country country;
	private List<Country> allCountries = new ArrayList<>();
	
	public Question(Country country) {
		this.country = country;
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

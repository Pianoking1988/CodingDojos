package de.heinemann.dojo.quiz.models;

public class Country {

	private String name;
	private String capital;

	public Country(String name, String capital) {
		this.name = name;
		this.capital = capital;
	}

	public String getName() {
		return name;
	}

	public String getCapital() {
		return capital;
	}

	@Override
	public String toString() {
		return new StringBuilder("Country [")
				.append("name=").append(name)
				.append(", capital=").append(capital)
				.append("]")
				.toString();
	}

}

package de.heinemann.dojo.quiz.model;

public enum QuestionType {

	ASK_FOR_CAPITAL (1, "Frage nach der Hauptstadt für ein Land", "Hauptstadt"),
	ASK_FOR_NAME (2, "Frage nach dem Land für eine Hauptstadt", "Land");
	
	private int value;
	private String description;
	private String keyword;
	
	private QuestionType(int value, String description, String keyword) {
		this.value = value;
		this.description = description;
		this.keyword = keyword;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
}

package de.heinemann.dojo.quiz.models;

public enum QuestionType {

	ASK_FOR_CAPITAL (1, "Frage nach der Hauptstadt für ein Land"),
	ASK_FOR_NAME (2, "Frage nach dem Land für eine Hauptstadt");
	
	private int value;
	private String description;
	
	private QuestionType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
}

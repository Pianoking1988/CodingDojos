package de.heinemann.dojo.quiz;

import java.io.File;
import java.util.List;

import de.heinemann.dojo.quiz.input.InputReaderImpl;
import de.heinemann.dojo.quiz.models.Country;
import de.heinemann.dojo.quiz.models.Question;
import de.heinemann.dojo.quiz.random.ShufflerImpl;

public class Application {

	public static void main(String[] args) {
		CountryReader countryReader = new CountryReader(new File("Länder und Hauptstädte.txt"));
		List<Country> countries = countryReader.readCountries();
		
		QuestionGenerator questionGenerator = new QuestionGenerator(10, new ShufflerImpl());
		List<Question> questions = questionGenerator.generateQuestions(countries);
		
		QuizMaster quizMaster = new QuizMaster(5, new InputReaderImpl());
		quizMaster.askQuestions(questions);
	}

}

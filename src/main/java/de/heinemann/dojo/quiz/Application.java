package de.heinemann.dojo.quiz;

import java.io.File;
import java.util.List;

import de.heinemann.dojo.quiz.input.InputReader;
import de.heinemann.dojo.quiz.input.InputReaderImpl;
import de.heinemann.dojo.quiz.models.Country;
import de.heinemann.dojo.quiz.models.Question;
import de.heinemann.dojo.quiz.models.QuestionType;
import de.heinemann.dojo.quiz.random.RandomImpl;

public class Application {

	public static void main(String[] args) {
		final InputReader inputReader = new InputReaderImpl();
		
		CountryReader countryReader = new CountryReader(new File("Länder und Hauptstädte.txt"));
		List<Country> countries = countryReader.readCountries();
		
		SettingsReader settingsReader = new SettingsReader(countries.size(), inputReader);
		int numberOfQuestions = settingsReader.readNumberOfQuestions();
		int numberOfAnswers = settingsReader.readNumberOfAnswers();
		List<QuestionType> questionTypes = settingsReader.readQuestionType();		
		
		QuestionGenerator questionGenerator = new QuestionGenerator(numberOfAnswers, questionTypes, new RandomImpl());
		List<Question> questions = questionGenerator.generateQuestions(countries);
		
		QuizMaster quizMaster = new QuizMaster(numberOfQuestions, inputReader);
		quizMaster.askQuestions(questions);
	}

}

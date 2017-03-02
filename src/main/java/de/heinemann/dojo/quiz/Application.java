package de.heinemann.dojo.quiz;

import java.io.File;
import java.util.List;

import de.heinemann.dojo.quiz.input.InputReader;
import de.heinemann.dojo.quiz.input.InputReaderImpl;
import de.heinemann.dojo.quiz.model.Country;
import de.heinemann.dojo.quiz.model.Question;
import de.heinemann.dojo.quiz.model.QuestionType;
import de.heinemann.dojo.quiz.random.RandomImpl;

public class Application {

	private final File INPUT_FILE = new File("Länder und Hauptstädte.txt");
	private final InputReader inputReader = new InputReaderImpl();
	
	public Application() {
		CountryReader countryReader = new CountryReader(INPUT_FILE);
		List<Country> countries = countryReader.readCountries();
		
		if (countries.size() > 1) {
			playQuiz(countries);
		} else {
			System.out.println("Für ein richtiges Quiz brauchen wir mindestens 2 Länder." 
					+ " Daher können wir nicht spielen.");
		}		
	}
	
	private void playQuiz(List<Country> countries) {
		SettingsReader settingsReader = new SettingsReader(countries.size(), inputReader);
		
		int numberOfQuestions = settingsReader.readNumberOfQuestions();
		int numberOfAnswers = settingsReader.readNumberOfAnswers();
		List<QuestionType> questionTypes = settingsReader.readQuestionType();		
		
		QuestionGenerator questionGenerator = new QuestionGenerator(numberOfAnswers, questionTypes, new RandomImpl());
		List<Question> questions = questionGenerator.generateQuestions(countries);
		
		QuizMaster quizMaster = new QuizMaster(numberOfQuestions, inputReader);
		quizMaster.askQuestions(questions);					
	}
	
	public static void main(String[] args) {
		new Application();
	}

}

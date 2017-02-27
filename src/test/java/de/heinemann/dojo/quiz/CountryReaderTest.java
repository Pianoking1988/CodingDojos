package de.heinemann.dojo.quiz;

import static de.heinemann.dojo.quiz.TestUtils.FRANCE;
import static de.heinemann.dojo.quiz.TestUtils.GERMANY;
import static de.heinemann.dojo.quiz.TestUtils.ITALY;
import static de.heinemann.dojo.quiz.TestUtils.SPAIN;
import static de.heinemann.dojo.quiz.TestUtils.countries;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;

import de.heinemann.dojo.quiz.models.Country;

public class CountryReaderTest {

	private CountryReader countryReader;
	
	private PrintStream out;
	private InOrder inOrder; 

	private void init(String filename) {
		out = mock(PrintStream.class);
		System.setOut(out);

		inOrder = inOrder(out);
		
		countryReader = new CountryReader(new File("./src/test/resources/" + filename));
	}
	
	@Test
	public void readingFromNonExistingFile() {
		init("notExisting.txt");
		
		List<Country> expected = countries();
		
		List<Country> actual = countryReader.readCountries();
		
		assertCountries(expected, actual);
		assertSystemOut("Leider konnte die Liste der Länder und Hauptstädte nicht gefunden werden.",
				"Bitte stelle sicher, dass sich die Datei notExisting.txt im selben Ordner wie die .jar Datei befindet.",
				"");
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	public void readingEmptyFile() {
		init("EmptyFile.txt");
		
		List<Country> expected = countries();
		
		List<Country> actual = countryReader.readCountries();
		
		assertCountries(expected, actual);
		assertSystemOut("Deine Eingabedatei ist leider leer.",
				"");
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	public void readingFourCorrectCountriesFromFile() {
		init("4 correct countries.txt");
		
		List<Country> expected = countries(GERMANY, FRANCE, ITALY, SPAIN);
		
		List<Country> actual = countryReader.readCountries();
		
		assertCountries(expected, actual);
		assertSystemOut("Für dein Spiel werden nun 4 Länder aus der Datei berücksichtigt.",
				"");
		inOrder.verifyNoMoreInteractions();
	}
		
	@Test
	public void readingTwoCorrectCountriesAndFourIncorrectCountriesFromFile() {
		init("2 correct countries and 4 incorrect countries.txt");
		
		List<Country> expected = countries(GERMANY, ITALY);
		
		List<Country> actual = countryReader.readCountries();
		
		assertCountries(expected, actual);
		assertSystemOut("4 Zeilen konnten in der Eingabedatei nicht gelesen werden:",
				"",
				"Zeile 2: Frankrei#ch#Paris",
				"Zeile 4: ",
				"Zeile 5: Spanien#",
				"Zeile 6: #Wien",
				"",
				"Diese Länder werden nun nicht berücksichtigt für dein Spiel.",
				"Korrigiere die Zeilen in der Eingabedatei um dies zu ändern.",
				"",
				"Für dein Spiel werden nun 2 Länder aus der Datei berücksichtigt.",
				"");
		
		inOrder.verifyNoMoreInteractions();
	}
	
	@Test
	public void readingNoCorrectCountriesAndThreeIncorrectCountriesFromFile() {
		init("0 correct countries and 3 incorrect countries.txt");
		
		List<Country> expected = countries();
		
		List<Country> actual = countryReader.readCountries();
		
		assertCountries(expected, actual);
		assertSystemOut("3 Zeilen konnten in der Eingabedatei nicht gelesen werden:",
				"",
				"Zeile 1: Frankrei#ch#Paris",
				"Zeile 2: ",
				"Zeile 3: Spanien#",
				"",
				"Diese Länder werden nun nicht berücksichtigt für dein Spiel.",
				"Korrigiere die Zeilen in der Eingabedatei um dies zu ändern.",
				"",
				"Für dein Spiel werden nun 0 Länder aus der Datei berücksichtigt.",
				"");
		
		inOrder.verifyNoMoreInteractions();
	}

	private void assertCountries(List<Country> expected, List<Country> actual) {
		assertEquals("Compare country list", expected.toString(), actual.toString());		
	}
	
	private void assertSystemOut(String... lines) {
		for (String line : lines) {
			inOrder.verify(out, times(1)).println(line);
		}
	}	

}

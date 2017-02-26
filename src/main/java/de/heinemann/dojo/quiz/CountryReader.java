package de.heinemann.dojo.quiz;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import de.heinemann.dojo.quiz.models.Country;

public class CountryReader {
	
	private File file;
	private List<Country> countries = new ArrayList<>();
	private Map<Integer, String> incorrectLines = new HashMap<>();
	
	public CountryReader(File file) {
		this.file = file;
	}

	public List<Country> readCountries() {
		if (!file.exists()) {
			System.out.println("Leider konnte die Liste der Länder und Hauptstädte nicht gefunden werden.");
			System.out.println("Bitte stelle sicher, dass sich die Datei " + file.getName()
					+ " im selben Ordner wie die .jar Datei befindet.");
			System.out.println("");
			return countries;
		}

		readAndEvaluateLines();
		
		if (countries.size() + incorrectLines.size() == 0) {
			System.out.println("Deine Eingabedatei ist leider leer.");
			System.out.println("");
		} else {
			printIncorrectLines();
			printCorrectLineInformation();
		}
		
		return countries;
	}

	private void readAndEvaluateLines() {
		List<String> lines = readLines();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (isLineMatchingFormat(line)) {
				countries.add(buildCountryFromLine(line));
			} else {
				incorrectLines.put(i + 1, line);
			}
		}		
	}

	private List<String> readLines() {
		try {
			return FileUtils.readLines(file, "Cp1252");
		} catch (Exception exception) {
			return new ArrayList<>();
		}
	}
	
	private boolean isLineMatchingFormat(String line) {
		return StringUtils.isNotBlank(line)
				&& StringUtils.countMatches(line, "#") == 1
				&& StringUtils.isNoneBlank(StringUtils.substringBefore(line, "#"))
				&& StringUtils.isNoneBlank(StringUtils.substringAfter(line, "#"));
	}
	
	private Country buildCountryFromLine(String line) {
		String[] lineParts = line.split("#");
		return new Country(lineParts[0].trim(), lineParts[1].trim());		
	}

	private void printIncorrectLines() {
		if (!incorrectLines.isEmpty()) {
			System.out.println(incorrectLines.size() + " Zeilen konnten in der Eingabedatei nicht gelesen werden:");
			System.out.println("");
			for (int lineNumber : new TreeSet<Integer>(incorrectLines.keySet())) {
				System.out.println("Zeile " + lineNumber + ": " + incorrectLines.get(lineNumber));
			}
			System.out.println("");
			System.out.println("Diese Länder werden nun nicht berücksichtigt für dein Spiel.");
			System.out.println("Korrigiere die Zeilen in der Eingabedatei um dies zu ändern.");
			System.out.println("");
		}		
	}

	private void printCorrectLineInformation() {
		System.out.println("Für dein Spiel werden nun " + countries.size() + " Länder aus der Datei berücksichtigt.");		
		System.out.println("");
	}

}

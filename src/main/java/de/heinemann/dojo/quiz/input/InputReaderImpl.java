package de.heinemann.dojo.quiz.input;

import java.util.Scanner;

public class InputReaderImpl implements InputReader {

	private Scanner scanner = new Scanner(System.in);
	
	@Override
	public String nextLine() {
		return scanner.nextLine();
	}

}

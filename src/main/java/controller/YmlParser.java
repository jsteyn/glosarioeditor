package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.TermStructure;

public class YmlParser {
	
	public static void main(String[] args) {
		System.out.println(readFile("glossary.yml"));
	}

	public static ArrayList<TermStructure> readFile(String filename) {
		ArrayList<TermStructure> data = new ArrayList<TermStructure>();
		try {
			Scanner fileScanner = new Scanner(new File(YmlParser.class.getClassLoader().getResource(filename).getFile()));
			String ymlSection = "";
			String nextLine;
			while (fileScanner.hasNext()) {
				nextLine = fileScanner.nextLine();
				if (nextLine.length() != 0 && nextLine.charAt(0) == '-') {
					if (!ymlSection.equals("")) {
						data.add(parseYml(ymlSection));
					}
					ymlSection = "";
				}
				ymlSection += nextLine + "\n";
			}
			if (!ymlSection.equals("")) {
				data.add(parseYml(ymlSection));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private static TermStructure parseYml(String ymlCode) {
		System.out.println("PARSE");
		TermStructure struct = new TermStructure();
		struct.setName(ymlCode.substring(8, ymlCode.indexOf('\n')));
		Matcher matcher = findMatch(ymlCode, "(\\s\\s\\w\\w:|\\z)");
		matcher.find();
		int startIndex = matcher.start();
		int endIndex;
		while (matcher.find()) {
			endIndex = matcher.start();
			System.out.println("MATCH " + startIndex + " " + endIndex);
			System.out.println(ymlCode.substring(startIndex, endIndex));
			
			int quoteStart = ymlCode.indexOf('"', startIndex);
			String language = ymlCode.substring(startIndex + 2, startIndex + 4);
			String term = ymlCode.substring(quoteStart + 1, ymlCode.indexOf('"', quoteStart + 1));
			String definition = ymlCode.substring(ymlCode.indexOf("def: >"));
			struct.addDefinition(language, definition);
			struct.setTerm(language, term);
			
			startIndex = endIndex;
		}
		return struct;
	}
	
	private static Matcher findMatch(String text, String regex) {
		return Pattern.compile(regex, Pattern.DOTALL).matcher(text);
	}
		
}

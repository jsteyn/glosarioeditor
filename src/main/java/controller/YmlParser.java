package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.DefinitionStructure;
import model.TermStructure;

public class YmlParser {
	
	public static void main(String[] args) {
		ArrayList<TermStructure> struct = readFile(new File(YmlParser.class.getClassLoader().getResource("glossary.yml").getFile()));
		System.out.println("Parsed Structure: " + struct);
		writeToFile("newGlossary.yml", struct);
	}
	
	public static ArrayList<TermStructure> readFile(String filename) {
		return readFile(new File(filename));
	}

	public static ArrayList<TermStructure> readFile(File file) {
		ArrayList<TermStructure> data = new ArrayList<TermStructure>();
		try {
			Scanner fileScanner = new Scanner(file);
			
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
		TermStructure struct = new TermStructure();
		struct.setName(ymlCode.substring(8, ymlCode.indexOf('\n')));
		
		Matcher matcher = findMatch(ymlCode, "(\\s\\s\\w\\w:|\\z)");
		matcher.find();
		
		int startIndex = matcher.start();
		int endIndex;
		while (matcher.find()) {
			endIndex = matcher.start();
			String languageSection = ymlCode.substring(startIndex, endIndex);
			int quoteStart = languageSection.indexOf('"');
			
			String language = languageSection.substring(2, 4);
			String term = languageSection.substring(quoteStart + 1, languageSection.indexOf('"', quoteStart + 1));
			String definition = "      " + languageSection.substring(languageSection.indexOf("def: >") + 6).trim();
			
			struct.addDefinition(language, definition);
			struct.setTerm(language, term);
			
			startIndex = endIndex;
		}
		return struct;
	}
	
	public static void writeToFile(String filename, ArrayList<TermStructure> structures) {
		writeToFile(new File(filename), structures);		
	}
	
	public static void writeToFile(File file, ArrayList<TermStructure> structures) {
		String data = "";
		for (TermStructure struct: structures) {
			data += parseStructure(struct) + "\n\n";
		}
		if (!data.equals("")) {
			data = data.trim();
		}
		try {
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String parseStructure(TermStructure struct) {
		String dataSection = "- slug: " + struct.getName();
		for (DefinitionStructure def: struct.getDefinitions()) {
			dataSection += "\n  " + def.language + ":";
			dataSection += "\n    term: \"" + def.term + "\"";
			dataSection += "\n    def: >\n" + def.definition;
		}
		return dataSection;
	}
	
	private static Matcher findMatch(String text, String regex) {
		return Pattern.compile(regex, Pattern.DOTALL).matcher(text);
	}
		
}

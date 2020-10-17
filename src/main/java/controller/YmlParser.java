package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		System.out.println("Written");
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
			fileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private static TermStructure parseYml(String ymlCode) {
		TermStructure struct = new TermStructure();
		struct.setId(ymlCode.substring(8, ymlCode.indexOf('\n')));
		Matcher refMatcher = findMatch(ymlCode, "  ref:\n");
		if (refMatcher.find()) {
			Matcher refIterator = findMatch(ymlCode, "    - ");
			while (refIterator.find()) {
				struct.addRef(ymlCode.substring(refIterator.start() + 6, ymlCode.indexOf('\n', refIterator.start())));
			}
		}
		
		Matcher languageMatcher = findMatch(ymlCode, "(\\s\\s\\w\\w:|\\z)");
		languageMatcher.find();
		
		int startIndex = languageMatcher.start();
		int endIndex;
		while (languageMatcher.find()) {
			endIndex = languageMatcher.start();
			String languageSection = ymlCode.substring(startIndex, endIndex);
			int quoteStart = languageSection.indexOf('"');
			
			String language = languageSection.substring(2, 4);
			String term = languageSection.substring(quoteStart + 1, languageSection.indexOf('"', quoteStart + 1));
			String definition = languageSection.substring(languageSection.indexOf("def: >") + 6).trim()
					.replaceAll("\n", "").replaceAll(" +", " ");
			String acronym = "";
			if (languageSection.contains("    acronym: ")) {
				acronym = languageSection.substring(languageSection.indexOf("    acronym: ") + 13,
						languageSection.indexOf('\n', languageSection.indexOf("    acronym: ")));
			}
			
			struct.addDefinition(language, definition);
			struct.setTerm(language, term);
			struct.setAcronym(language, acronym);
			
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
		String dataSection = "- slug: " + struct.getId();
		ArrayList<String> refs = struct.getRefs();
		if (refs.size() != 0) {
			dataSection += "\n  ref:";
			for (String ref: refs) {
				dataSection += "\n    - " + ref;
			}
		}
		for (DefinitionStructure def: struct.getDefinitions()) {
			dataSection += "\n  " + def.language + ":";
			dataSection += "\n    term: \"" + def.term + "\"";
			if (!def.acronym.equals("")) {
				dataSection += "\n    acronym: " + def.acronym;
			}
			dataSection += "\n    def: >";
			String definition = def.definition.trim().replaceAll("\n", "").replaceAll(" +", " ");
			int startIndex = 0;
			int endIndex = 0;
			int nextIndex;
			while ((nextIndex = definition.indexOf(" ", endIndex + 1)) != -1) {
				if (nextIndex - startIndex > 80) {
					dataSection += "\n      " + definition.substring(startIndex, endIndex);
					startIndex = endIndex + 1;
				} else {
					endIndex = nextIndex;
				}
			}
			if (startIndex < endIndex) {
				dataSection += "\n      " + definition.substring(startIndex);
			}
		}
		return dataSection;
	}
	
	private static Matcher findMatch(String text, String regex) {
		return Pattern.compile(regex, Pattern.DOTALL).matcher(text);
	}
		
}

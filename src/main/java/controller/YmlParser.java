package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.DefinitionStructure;
import model.InvalidYmlSyntaxException;
import model.TermStructure;

public class YmlParser {
	
	private static Logger logger = LoggerFactory.getLogger(YmlParser.class);
	
	private static Pattern languagePattern = Pattern.compile("(\\s\\s\\w\\w:|\\z)", Pattern.DOTALL);
	private static Pattern refPattern = Pattern.compile("\\s\\sref:\\n", Pattern.DOTALL);
	private static Pattern refIterPattern = Pattern.compile("\\s\\s\\s\\s-\\s", Pattern.DOTALL);
	
	private static Pattern languageCheck = Pattern.compile("\\s\\s\\w\\w:", Pattern.DOTALL);
	private static Pattern termCheck = Pattern.compile("\\s\\s\\s\\sterm:\\s", Pattern.DOTALL);
	private static Pattern defCheck = Pattern.compile("\\s\\s\\s\\sdef:\\s>", Pattern.DOTALL);
	
	public static int counter = -1;
	
	public static void main(String[] args) {
		try {
			ArrayList<TermStructure> struct = readFile(args[0]);
			logger.trace(args[0] + " has been validated");
		} catch (InvalidYmlSyntaxException e) {

		} catch (FileNotFoundException e) {

		}
	}
	
	public static ArrayList<TermStructure> readFile(String filename) throws InvalidYmlSyntaxException, FileNotFoundException {
		return readFile(new File(filename));
	}

	public static ArrayList<TermStructure> readFile(File file) throws InvalidYmlSyntaxException, FileNotFoundException {
		logger.trace("Reading from file: " + file.getAbsolutePath());
		if (counter != -1) {
			logger.error("File read already in progress");
			return null;
		}
		
		counter = 1;
		String ymlSection = "";
		ArrayList<TermStructure> data = new ArrayList<TermStructure>();
		try {
			Scanner fileScanner = new Scanner(file);
			
			String nextLine;
			int subCounter = 0;
			while (fileScanner.hasNext()) {
				nextLine = fileScanner.nextLine();
				if (nextLine.length() != 0 && nextLine.charAt(0) == '-') {
					if (!ymlSection.equals("")) {
						data.add(parseYml(ymlSection));
					}
					ymlSection = "";
					counter += subCounter;
					subCounter = 0;
				}
				ymlSection += nextLine + "\n";
				subCounter++;
			}
			if (!ymlSection.equals("")) {
				data.add(parseYml(ymlSection));
				counter += subCounter;
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} catch (InvalidYmlSyntaxException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			InvalidYmlSyntaxException error = new InvalidYmlSyntaxException(counter, ymlSection, InvalidYmlSyntaxException.UNKNOWN_ERROR);
			logger.error(error.getMessage());
			throw error;
		}
		logger.trace("Finished reading from file: " + file.getAbsolutePath());
		counter = -1;
		return data;
	}
	
	private static TermStructure parseYml(String ymlCode) throws InvalidYmlSyntaxException {
		TermStructure struct = new TermStructure();
		struct.setId(ymlCode.substring(8, ymlCode.indexOf('\n')));
		Matcher refMatcher = refPattern.matcher(ymlCode);
		if (refMatcher.find()) {
			Matcher refIterator = refIterPattern.matcher(ymlCode);
			while (refIterator.find()) {
				struct.addRef(ymlCode.substring(refIterator.start() + 6, ymlCode.indexOf('\n', refIterator.start())));
			}
		}
		
		if (!languageCheck.matcher(ymlCode).find()) {
			throw new InvalidYmlSyntaxException(counter, ymlCode, InvalidYmlSyntaxException.MISSING_LANGUAGE);
		}
		
		Matcher languageMatcher = languagePattern.matcher(ymlCode);
		languageMatcher.find();
		
		int startIndex = languageMatcher.start();
		int endIndex;
		int subCounter = 1;
		while (languageMatcher.find()) {
			endIndex = languageMatcher.start();
			String languageSection = ymlCode.substring(startIndex, endIndex);
			
			if (!termCheck.matcher(languageSection).find()) {
				throw new InvalidYmlSyntaxException(counter + subCounter, languageSection,
						InvalidYmlSyntaxException.MISSING_TERM);
			}
			if (!defCheck.matcher(languageSection).find()) {
				throw new InvalidYmlSyntaxException(counter + subCounter, languageSection,
						InvalidYmlSyntaxException.MISSING_DEF);
			}
			
			int quoteStart = languageSection.indexOf('"');
			if (quoteStart == -1 || languageSection.indexOf('"', quoteStart + 1) == -1) {
				throw new InvalidYmlSyntaxException(counter + subCounter, languageSection,
						InvalidYmlSyntaxException.MISSING_QUOTES);
			}
			
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
			
			subCounter += languageSection.split("\n").length;
			
			startIndex = endIndex;
		}
		return struct;
	}
	
	public static void writeToFile(String filename, ArrayList<TermStructure> structures) {
		writeToFile(new File(filename), structures);		
	}
	
	public static void writeToFile(File file, ArrayList<TermStructure> structures) {
		logger.trace("Writing to file: " + file.getAbsolutePath());
		String data = "";
		for (TermStructure struct: structures) {
			String newData = parseStructure(struct);
			if (!newData.equals("")) {
				data += parseStructure(struct) + "\n\n";
			}
		}
		if (!data.equals("")) {
			data = data.trim();
		}
		try {
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(data + "\n\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.trace("Finished writing to file: " + file.getAbsolutePath());
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
		String check = dataSection.substring(0, dataSection.length());
		for (DefinitionStructure def: struct.getDefinitions()) {
			if (!(def.term.equals("") || def.definition.equals(""))) {
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
		}
		if (dataSection.equals(check)) {
			return "";
		}
		return dataSection;
	}
	
}

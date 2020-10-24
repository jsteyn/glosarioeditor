package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.InvalidYmlSyntaxException;
import model.TermStructure;
import view.MainFrame;

public class Root {
	
	private static final long serialVersionUID = 1L;
	
	public boolean lockDocumentListeners = false;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private MainFrame mainFrame;
	
	private final JFileChooser fc = new JFileChooser();
	
	public static void main(String[] args) {
		new Root();
	}
	
	public Root() {
		FileFilter ff = new FileFilter() {
			@Override
			public String getDescription() {
				return "YAML Documents (*.yml)";
			}
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || Pattern.compile("[a-zA-Z0-9_]+\\.yml", Pattern.DOTALL).matcher(f.getName()).matches();
			}
		};
		fc.addChoosableFileFilter(ff);
		fc.setFileFilter(ff);
		mainFrame = new MainFrame(this);
		
		File file = new File("system.properties");
		try {
			if (file.createNewFile()) {
				Properties p = new Properties();
				p.setProperty("lastFile", "sampleGlossary.yml");
				p.store(new BufferedWriter(new FileWriter(file)), "");
				
				File defaultFile = new File("src/main/resources/sampleGlossaryDefault.yml");
				File copyFile = new File("sampleGlossary.yml");
				copyFile.createNewFile();
				BufferedWriter copyWriter = new BufferedWriter(new FileWriter(copyFile));
				copyWriter.write(Files.readString(defaultFile.toPath()));
				copyWriter.close();
				
				chooseFile(new File("sampleGlossary.yml"));
			} else {
				Properties p = new Properties();
				try {
					p.load(new BufferedReader(new FileReader(file)));
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage());
				}
				chooseFile(new File(p.getProperty("lastFile")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewTerm() {
		mainFrame.selectorPanel.selectButton(mainFrame.selectorPanel.addButton(new TermStructure()));
	}
	public void removeTerm() {
		mainFrame.selectorPanel.removeSelected();
	}
	public void chooseFile() {
		int returnVal = fc.showDialog(mainFrame, "Open File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			chooseFile(file);
		}
	}
	public void chooseFile(File file) {
		mainFrame.currentFile = file;
		lockDocumentListeners = true;
		mainFrame.fileLabel.setText(file.getAbsolutePath());
		lockDocumentListeners = false;
		ArrayList<TermStructure> structs;
		try {
			structs = YmlParser.readFile(file);
		} catch (InvalidYmlSyntaxException e) {
			JOptionPane.showMessageDialog(mainFrame, e.getMessage());
			return;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(mainFrame, file.getAbsolutePath() + " does not exist");
			return;
		}
		mainFrame.selectorPanel.clearButtons();
		for (TermStructure s: structs) {
			mainFrame.selectorPanel.addButton(s);
		}
		Properties p = new Properties();
		p.setProperty("lastFile", file.getAbsolutePath());
		try {
			p.store(new BufferedWriter(new FileWriter("system.properties")), "");
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	public void saveFile() {
		YmlParser.writeToFile(mainFrame.currentFile, mainFrame.selectorPanel.getStructures());
	}
	
	public void addLanguage() {
		String language = mainFrame.structurePanel.languageSelect.inputField.getText();
		if (Pattern.compile("^[a-z][a-z]$", Pattern.DOTALL).matcher(language).matches()) {
			mainFrame.selectorPanel.getSelected().getStruct().addDefinition(language, "");
			mainFrame.structurePanel.languageSelect.addItem(language);
		}
	}
	
	public void onIdSelect() {
		mainFrame.setSelection();
		mainFrame.checkEnabled();
	}
	public void onIdChange() {
		if (mainFrame.selectorPanel.getSelected() != null) {
			lockDocumentListeners = true;
			mainFrame.selectorPanel.getSelected().getStruct()
			.setId(mainFrame.structurePanel.idField.getText());
			mainFrame.selectorPanel.getSelected().setText(mainFrame.structurePanel.idField.getText());
			lockDocumentListeners = false;
		}
	}
	public void onLanguageSelect() {
		mainFrame.checkEnabled();
		mainFrame.structurePanel.setLanguage(mainFrame.selectorPanel.getSelectedStructure());
		mainFrame.readOnlyPanel.setLanguage(mainFrame.selectorPanel.getSelectedStructure());
	}
	public void onTermChange() {
		mainFrame.selectorPanel.getSelected().getStruct()
		.setTerm((String)mainFrame.structurePanel.languageSelect.getSelectedItem(),
				mainFrame.structurePanel.termField.getText());
	}
	public void onAcronymChange() {
		mainFrame.selectorPanel.getSelected().getStruct()
		.setAcronym((String)mainFrame.structurePanel.languageSelect.getSelectedItem(),
				mainFrame.structurePanel.acronymField.getText());
	}
	public void onDefinitionChange() {
		mainFrame.selectorPanel.getSelected().getStruct()
		.setDefinition((String)mainFrame.structurePanel.languageSelect.getSelectedItem(),
				mainFrame.structurePanel.definitionArea.getText());
	}
	public void onReferenceChange() {
		mainFrame.referencePanel.applyReferences(mainFrame.selectorPanel.getSelected().getStruct());
	}

}

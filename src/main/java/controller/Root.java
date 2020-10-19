package controller;

import java.awt.SplashScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import model.InvalidYmlSyntaxException;
import model.TermStructure;
import view.MainFrame;

public class Root {
	
	private static final long serialVersionUID = 1L;
	
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
			mainFrame.currentFile = file;
			mainFrame.fileLabel.setText(file.getAbsolutePath());
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
		}
	}
	public void saveFile() {
		YmlParser.writeToFile(mainFrame.currentFile, mainFrame.selectorPanel.getStructures());
	}
	
	public void addLanguage() {
		String language = mainFrame.languageSelect.inputField.getText();
		if (Pattern.compile("^[a-z][a-z]$", Pattern.DOTALL).matcher(language).matches()) {
			mainFrame.selectorPanel.getSelected().getStruct().addDefinition(language, "");
			mainFrame.languageSelect.addItem(language);
		}
	}
	
	public void onIdSelect() {
		mainFrame.setSelection();
		mainFrame.checkEnabled();
	}
	public void onIdChange() {
		if (mainFrame.selectorPanel.getSelected() != null) {
			mainFrame.selectorPanel.getSelected().getStruct()
			.setId(mainFrame.idField.getText());
			mainFrame.selectorPanel.getSelected().setText(mainFrame.idField.getText());
		}
	}
	public void onLanguageSelect() {
		mainFrame.checkEnabled();
		mainFrame.setLanguage();
	}
	public void onTermChange() {
		mainFrame.selectorPanel.getSelected().getStruct()
		.setTerm((String)mainFrame.languageSelect.getSelectedItem(), mainFrame.termField.getText());
	}
	public void onAcronymChange() {
		mainFrame.selectorPanel.getSelected().getStruct()
		.setAcronym((String)mainFrame.languageSelect.getSelectedItem(), mainFrame.acronymField.getText());
	}
	public void onDefinitionChange() {
		mainFrame.selectorPanel.getSelected().getStruct()
		.setDefinition((String)mainFrame.languageSelect.getSelectedItem(), mainFrame.definitionArea.getText());
	}
	public void onReferenceChange() {
		System.out.println("REFERENCE");
		mainFrame.referencePanel.applyReferences(mainFrame.selectorPanel.getSelected().getStruct());
	}
	
	public void selectFile() {
		System.out.println("FILE SELECTED");
	}
	public void openFile() {
		System.out.println("FILE OPENED");
	}

}

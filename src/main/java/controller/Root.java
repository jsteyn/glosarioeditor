package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

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
		System.out.println("NEW TERM");
	}
	public void removeTerm() {
		System.out.println("REMOVE TERM");
	}
	public void chooseFile() {
		int returnVal = fc.showDialog(mainFrame, "Open File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			mainFrame.currentFile = file;
			mainFrame.fileLabel.setText(file.getAbsolutePath());
			ArrayList<TermStructure> structs = YmlParser.readFile(file);
			mainFrame.selectorPanel.clearButtons();
			for (TermStructure s: structs) {
				mainFrame.selectorPanel.addButton(s);
			}
		}
	}
	public void saveFile() {
		System.out.println("FILE SAVE");
	}
	
	public void addLanguage() {
		System.out.println("ADD LANGUAGE");
	}
	
	public void onIdSelect() {
		mainFrame.setSelection();
		mainFrame.checkEnabled();
	}
	public void onIdChange() {
		System.out.println("NAME CHANGED");
	}
	public void onLanguageSelect() {
		mainFrame.checkEnabled();
		mainFrame.setLanguage();
	}
	public void onTermChange() {
		System.out.println("TERM CHANGED");
	}
	public void onAcronymChange() {
		System.out.println("ACRONYM CHANGED");
	}
	public void onDefinitionChange() {
		System.out.println("DEFINITION CHANGED");
	}
	
	public void selectFile() {
		System.out.println("FILE SELECTED");
	}
	public void openFile() {
		System.out.println("FILE OPENED");
	}

}

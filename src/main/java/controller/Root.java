package controller;

import java.util.ArrayList;

import model.TermStructure;
import view.MainFrame;

public class Root {
	
	private static final long serialVersionUID = 1L;
	
	private MainFrame mainFrame;
	
	public static void main(String[] args) {
		new Root();
	}
	
	public Root() {
		mainFrame = new MainFrame(this);
	}

	
	public void addNewTerm() {
		System.out.println("NEW TERM");
	}
	public void removeTerm() {
		System.out.println("REMOVE TERM");
	}
	public void chooseFile() {
		System.out.println("FILE CHOOSE");
	}
	public void saveFile() {
		System.out.println("FILE SAVE");
	}
	
	public void selectLanguage() {
		System.out.println("SELECT LANGUAGE");
	}
	public void addLanguage() {
		System.out.println("ADD LANGUAGE");
	}
	
	public void onIdSelect() {
		mainFrame.checkEnabled();
		System.out.println("NAME SELECTED " + mainFrame.selectorPanel.getSelected().getStruct().getName());
	}
	public void onIdChange() {
		System.out.println("NAME CHANGED");
	}
	public void onLanguageSelect() {
		mainFrame.checkEnabled();
		System.out.println("LANGUAGE SELECTED " + mainFrame.languageSelect.getSelectedItem());
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

package model;

import java.util.ArrayList;
import java.util.HashMap;

public class TermStructure {
	
	private String term = "";
	private HashMap<String, String> definitions = new HashMap<String, String>();
	
	public TermStructure() {
		
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	public String getTerm() {
		return term;
	}
	
	public void setDefinition(String language, String definition) {
		definitions.put(language, definition);
	}
	public String getDefinition(String language) {
		return definitions.getOrDefault(language, "");
	}

}

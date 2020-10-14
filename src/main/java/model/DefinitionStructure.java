package model;

public class DefinitionStructure {
	public String language = "";
	public String term = "";
	public String definition = "";
	
	public DefinitionStructure(String language) {
		this.language = language;
	}
	
	public DefinitionStructure(String language, String definition) {
		this.language = language;
		this.definition = definition;
	}
	
	@Override
	public String toString() {
		return language + ":" + term;
	}
}

package model;

import java.util.LinkedHashMap;

public class TermStructure {
	
	private String name = "";
	private LinkedHashMap<String, DefinitionStructure> definitions = new LinkedHashMap<String, DefinitionStructure>();
	
	public TermStructure() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void addDefinition(String language, String definition) {
		if (definitions.containsKey(language)) {
			definitions.get(language).definition = definition;
		} else {
			definitions.put(language, new DefinitionStructure(language, definition));
		}
	}
	public String getDefinition(String language) {
		return definitions.getOrDefault(language, new DefinitionStructure(language)).definition;
	}
	public void setTerm(String language, String term) {
		definitions.get(language).term = term;
	}
	
	@Override
	public String toString() {
		return name + "-" + definitions;
	}

}

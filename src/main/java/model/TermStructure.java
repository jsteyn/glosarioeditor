package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TermStructure {
	
	private String name = "";
	private ArrayList<String> refs = new ArrayList<String>();
	private LinkedHashMap<String, DefinitionStructure> definitions = new LinkedHashMap<String, DefinitionStructure>();
	
	public TermStructure() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void addRef(String ref) {
		refs.add(ref);
	}
	public ArrayList<String> getRefs() {
		return refs;
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
	public ArrayList<DefinitionStructure> getDefinitions() {
		return new ArrayList(definitions.values());
	}
	
	public void setTerm(String language, String term) {
		definitions.get(language).term = term;
	}
	public void setAcronym(String language, String acronym) {
		definitions.get(language).acronym = acronym;
	}
	
	@Override
	public String toString() {
		return name + "-" + definitions + (refs.size() == 0 ? "" : "-REF:" + refs);
	}

}

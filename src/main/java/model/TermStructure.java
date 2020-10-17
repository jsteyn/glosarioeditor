package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TermStructure {
		
	private String id = "";
	private ArrayList<String> refs = new ArrayList<String>();
	private LinkedHashMap<String, DefinitionStructure> definitions = new LinkedHashMap<String, DefinitionStructure>();
	
	public TermStructure() {
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
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
	public String getTerm(String language) {
		return definitions.getOrDefault(language, new DefinitionStructure(language)).term;
	}
	public String getAcronym(String language) {
		return definitions.getOrDefault(language, new DefinitionStructure(language)).acronym;
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
	
	public ArrayList<String> getLanguages() {
		return new ArrayList<String>(definitions.keySet());
	}
	
	@Override
	public String toString() {
		return id + "-" + definitions + (refs.size() == 0 ? "" : "-REF:" + refs);
	}

}

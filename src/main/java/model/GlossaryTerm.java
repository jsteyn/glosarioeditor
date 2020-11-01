package model;


public class GlossaryTerm {


private String slug;
private GlossaryLanguages languages;

public String getSlug() {
return slug;
}

public void setSlug(String slug) {
this.slug = slug;
}

public GlossaryLanguages getLanguages() {
    return languages;
}

public void setLanguages(GlossaryLanguages languages) {
    this.languages = languages;
}

}
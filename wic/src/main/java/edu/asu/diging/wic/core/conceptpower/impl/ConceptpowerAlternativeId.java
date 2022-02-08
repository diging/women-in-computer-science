package edu.asu.diging.wic.core.conceptpower.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.asu.diging.wic.core.conceptpower.IConceptEntryId;

public class ConceptpowerAlternativeId implements IConceptEntryId {

    @JsonProperty("concept_id")
    private String conceptId;
    @JsonProperty("concept_uri")
    private String conceptUri;
    
    @Override
    public String getConceptId() {
        return conceptId;
    }
    
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }
    
    @Override
    public String getConceptUri() {
        return conceptUri;
    }
    
    public void setConceptUri(String conceptUri) {
        this.conceptUri = conceptUri;
    }
}

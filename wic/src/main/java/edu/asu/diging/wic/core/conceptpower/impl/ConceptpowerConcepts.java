package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConceptpowerConcepts {

    private List<ConceptpowerConcept> conceptEntries;
    
    @JsonIgnore
    private String pagination;

    public List<ConceptpowerConcept> getConceptEntries() {
        return conceptEntries;
    }

    public void setConceptEntries(List<ConceptpowerConcept> conceptEntries) {
        this.conceptEntries = conceptEntries;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }
}

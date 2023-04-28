package edu.asu.diging.wic.core.model.impl;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.asu.diging.wic.core.model.IConceptpowerAlternativeUrl;

@Entity
@Table(name = "tbl_conceptpower_alternativeuris")
public class ConceptpowerAlternativeUri implements IConceptpowerAlternativeUrl {

    @Id
    private String id;
    private String alternativeUris;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAlternativeUris() {
        return alternativeUris;
    }

    @Override
    public void setAlternativeUris(String alternativeUris) {
        this.alternativeUris = alternativeUris;
    }
}

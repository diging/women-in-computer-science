package edu.asu.diging.wic.core.conceptpower.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import edu.asu.diging.wic.core.conceptpower.IConceptEntryId;

@XmlType(name = "id", namespace = "http://www.digitalhps.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptEntryId implements IConceptEntryId {

    @XmlAttribute(name = "concept_id")
    private String conceptId;
    @XmlAttribute(name = "concept_uri")
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

package edu.asu.diging.wic.core.conceptpower.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "id", namespace = "http://www.digitalhps.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptEntryId {

    @XmlAttribute(name = "concept_id")
    private String conceptId;
    @XmlAttribute(name = "concept_uri")
    private String conceptUri;

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getConceptUri() {
        return conceptUri;
    }

    public void setConceptUri(String conceptUri) {
        this.conceptUri = conceptUri;
    }

}

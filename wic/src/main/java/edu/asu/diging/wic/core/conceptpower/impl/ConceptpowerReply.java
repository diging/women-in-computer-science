package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Maps and holds the Conceptpower XML API response for fetching Concept entries
 * @author Maulik Limbadiya
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "conceptpowerReply")
public class ConceptpowerReply {
    
    @XmlElement(name = "conceptEntry", namespace = "http://www.digitalhps.org/")
    private List<ConceptEntry> conceptEntries;

    public List<ConceptEntry> getConceptEntries() {
        return conceptEntries;
    }

    public void setConceptEntries(List<ConceptEntry> conceptEntries) {
        this.conceptEntries = conceptEntries;
    }
}

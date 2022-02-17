package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import edu.asu.diging.wic.core.conceptpower.IConceptEntry;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conceptEntry", namespace = "http://www.digitalhps.org/")
public class ConceptEntry implements IConceptEntry {
    
    @XmlElement(name = "id", namespace = "http://www.digitalhps.org/")
    private ConceptEntryId coneptEntryId;
    
    @XmlElement(name = "lemma", namespace = "http://www.digitalhps.org/")
    private String lemma;
    
    @XmlElement(name = "pos", namespace = "http://www.digitalhps.org/")
    private String pos;
    
    @XmlElement(name = "description", namespace = "http://www.digitalhps.org/")
    private String description;
    
    @XmlElement(name = "conceptList", namespace = "http://www.digitalhps.org/")
    private String conceptList;
    
    @XmlElement(name = "creator_id", namespace = "http://www.digitalhps.org/")
    private String creatorId;

    @XmlElement(name = "deleted", namespace = "http://www.digitalhps.org/")
    private boolean deleted;
    
    @XmlElement(name = "equal_to", namespace = "http://www.digitalhps.org/")
    private String equalTo;
    
    @XmlElement(name = "similar_to", namespace = "http://www.digitalhps.org/")
    private String similarTo;
    
    @XmlElement(name = "synonym_ids", namespace = "http://www.digitalhps.org/")
    private String synonymIds;
    
    @XmlElement(name = "type", namespace = "http://www.digitalhps.org/")
    private ConceptpowerEntryType type;
    
    @XmlElement(name = "wordnet_id", namespace = "http://www.digitalhps.org/")
    private List<String> wordnetIds;
    
    @XmlElementWrapper(name = "alternativeIds", namespace = "http://www.digitalhps.org/")
    @XmlElement(name = "id", namespace = "http://www.digitalhps.org/")
    private List<ConceptEntryId> alternativeIds;
    
    @Override
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    
    public ConceptEntryId getConeptEntryId() {
        return coneptEntryId;
    }

    public void setConeptEntryId(ConceptEntryId coneptEntryId) {
        this.coneptEntryId = coneptEntryId;
    }

    @Override
    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    @Override
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getConceptList() {
        return conceptList;
    }

    public void setConceptList(String conceptList) {
        this.conceptList = conceptList;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public List<String> getEqualTo() {
        if (equalTo == null) {
            return null;
        }
        return Arrays.asList(equalTo.split(","));
    }

    public void setEqualTo(String equalTo) {
        this.equalTo = equalTo;
    }

    @Override
    public List<String> getSimilarTo() {
        if (similarTo == null) {
            return null;
        }
        return Arrays.asList(similarTo.split(","));
    }

    public void setSimilarTo(String similarTo) {
        this.similarTo = similarTo;
    }

    public String getSynonymIds() {
        return synonymIds;
    }

    public void setSynonymIds(String synonymIds) {
        this.synonymIds = synonymIds;
    }

    @Override
    public ConceptpowerEntryType getType() {
        return type;
    }

    public void setType(ConceptpowerEntryType type) {
        this.type = type;
    }

    @Override
    public List<String> getWordnetIds() {
        return wordnetIds;
    }

    public void setWordnetIds(List<String> wordnetIds) {
        this.wordnetIds = wordnetIds;
    }
    
    @Override
    public List<ConceptEntryId> getAlternativeIds() {
        return alternativeIds;
    }

    public void setAlternativeIds(List<ConceptEntryId> alternativeIds) {
        this.alternativeIds = alternativeIds;
    }

    @Override
    public String getConceptId() {
        return this.coneptEntryId.getConceptId();
    }

    @Override
    public String getConceptUri() {
        return this.coneptEntryId.getConceptUri();
    }

}

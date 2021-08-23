package edu.asu.diging.wic.core.model.impl;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ConceptText")
public class ConceptText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String conceptId;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    @Lob
    private String text;
    private String addedOn;
    private String addedBy;
    private String modifiedOn;
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedOn() {
        return addedOn;
    }

    /**
     * Setter will convert the OffsetDateTime Object to ISO 8601 string format
     * @param addedOn Object of OffsetDateTime
     */
    public void setAddedOn(OffsetDateTime addedOn) {
        this.addedOn = addedOn.toString();
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    /**
     * Setter will convert the OffsetDateTime Object to ISO 8601 string format
     * @param modifiedOn Object of OffsetDateTime
     */
    public void setModifiedOn(OffsetDateTime modifiedOn) {
        this.modifiedOn = modifiedOn.toString();
    }

    public String getModifiedby() {
        return modifiedBy;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedBy = modifiedby;
    }
}

package edu.asu.diging.wic.core.conceptText.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ConceptText")
public class ConceptText {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conceptId;
    private String text;
    
	
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
}

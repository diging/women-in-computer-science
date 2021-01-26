package edu.asu.diging.wic.core.conceptText.service;

import java.util.List;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;

public interface IConceptTextService {
	
	void addText(ConceptText conceptText);
	
	List<ConceptText> allTextOfConcept(String conceptId);
	
	List<ConceptText> showAllText();
	
	void deleteText(String id);
	
	void updateText(String id, String text, String modifiedBy);
	
	public ConceptText getConceptTextById(String id);
}

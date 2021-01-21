package edu.asu.diging.wic.core.conceptText.service;

import java.util.List;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;

public interface IConceptTextService {
	
	public void addText(ConceptText conceptText);
	
	public List<ConceptText> allTextOfConcept(String conceptId);
	
	public List<ConceptText> showAllText();
	
	public void deleteText(String id);
	
	public void updateText(String id, String text, String modifiedBy);
	
	public ConceptText getConceptTextById(String id);
}

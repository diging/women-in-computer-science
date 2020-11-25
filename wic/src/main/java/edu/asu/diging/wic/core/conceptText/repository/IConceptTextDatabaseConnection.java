package edu.asu.diging.wic.core.conceptText.repository;

import java.util.List;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;

public interface IConceptTextDatabaseConnection {
	
	List<ConceptText> allTextOfConcept(String conceptId);
	void addText(ConceptText conceptText);
	public List<ConceptText> showAllText();
	void update(String id, String text);
	void delete(String id);
	
}

package edu.asu.diging.wic.core.conceptText.db;

import java.util.List;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;

public interface IConceptTextDatabaseConnection {
	
	List<ConceptText> allTextOfConcept(String conceptId);
	void addText(String text, String conceptId);
	void update(String id, String text);
	void delete(String id);
}

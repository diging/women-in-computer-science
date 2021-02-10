package edu.asu.diging.wic.core.conceptText.repository;

import java.util.List;

import edu.asu.diging.wic.core.model.impl.ConceptText;

public interface IConceptTextDatabaseConnection {
	
	void add(ConceptText conceptText);
	List<ConceptText> findAll();
	void update(ConceptText updatedForm, String modifiedBy);
	void delete(String id);
	ConceptText getConceptTextById(String id);
	
}

package edu.asu.diging.wic.core.conceptText.service;

import java.util.List;

import edu.asu.diging.wic.core.model.impl.ConceptText;

public interface IConceptTextService {
	
	void addText(ConceptText conceptText);

	List<ConceptText> findAll(String pageNumber);
	
	void deleteText(String id);
	
	void updateText(ConceptText updatedForm, String modifiedBy);
	
	ConceptText getConceptTextById(String id);
	
	int getCount();
}

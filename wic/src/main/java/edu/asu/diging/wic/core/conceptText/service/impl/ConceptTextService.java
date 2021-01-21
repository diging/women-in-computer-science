package edu.asu.diging.wic.core.conceptText.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;
import edu.asu.diging.wic.core.conceptText.repository.IConceptTextDatabaseConnection;
import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;

@Service
public class ConceptTextService implements IConceptTextService {
	
	@Autowired
	IConceptTextDatabaseConnection iConceptTextDatabaseConnection;
	
	@Override
	public void addText(ConceptText conceptText) {
		// TODO Auto-generated method stub
		iConceptTextDatabaseConnection.addText(conceptText);
	}

	@Override
	public List<ConceptText> allTextOfConcept(String conceptId) {
		
		return iConceptTextDatabaseConnection.allTextOfConcept(conceptId);
	}

	@Override
	public List<ConceptText> showAllText() {
		return iConceptTextDatabaseConnection.showAllText();
	}

	@Override
	public void deleteText(String id) {
		// TODO Auto-generated method stub
		iConceptTextDatabaseConnection.delete(id);
	}

	@Override
	public void updateText(String id, String text, String modifiedBy) {
		// TODO Auto-generated method stub
		iConceptTextDatabaseConnection.update(id, text, modifiedBy);
	}
	
	@Override
	public ConceptText getConceptTextById(String id) {
		// TODO Auto-generated method stub
		ConceptText data = iConceptTextDatabaseConnection.getConceptTextById(id);
		return data;
	}
	

}

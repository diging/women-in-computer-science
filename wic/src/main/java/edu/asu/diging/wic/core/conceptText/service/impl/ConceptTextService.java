package edu.asu.diging.wic.core.conceptText.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.conceptText.repository.IConceptTextDatabaseConnection;
import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;
import edu.asu.diging.wic.core.model.impl.ConceptText;

@Service
public class ConceptTextService implements IConceptTextService {
	
	@Autowired
	IConceptTextDatabaseConnection iConceptTextDatabaseConnection;
	
	@Override
	public void addText(ConceptText conceptText) {
		// TODO Auto-generated method stub
		iConceptTextDatabaseConnection.add(conceptText);
	}

	@Override
	public List<ConceptText> findAll() {
		return iConceptTextDatabaseConnection.findAll();
	}

	@Override
	public void deleteText(String id) {
		// TODO Auto-generated method stub
		iConceptTextDatabaseConnection.delete(id);
	}

	@Override
	public void updateText(ConceptText updatedForm, String modifiedBy) {
		// TODO Auto-generated method stub
		iConceptTextDatabaseConnection.update(updatedForm, modifiedBy);
	}
	
	@Override
	public ConceptText getConceptTextById(String id) {
		// TODO Auto-generated method stub
		ConceptText data = iConceptTextDatabaseConnection.getConceptTextById(id);
		return data;
	}
	

}

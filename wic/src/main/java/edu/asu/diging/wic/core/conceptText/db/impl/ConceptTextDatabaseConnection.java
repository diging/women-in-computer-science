package edu.asu.diging.wic.core.conceptText.db.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptText.db.IConceptTextDatabaseConnection;
import edu.asu.diging.wic.core.conceptText.model.ConceptText;

@Repository
@Transactional
public class ConceptTextDatabaseConnection implements IConceptTextDatabaseConnection {

	@Override
	public List<ConceptText> allTextOfConcept(String conceptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addText(String text, String conceptId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String id, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

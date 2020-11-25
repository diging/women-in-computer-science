package edu.asu.diging.wic.core.conceptText.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;
import edu.asu.diging.wic.core.conceptText.repository.IConceptTextDatabaseConnection;
import edu.asu.diging.wic.core.model.impl.Concept;
import edu.asu.diging.wic.core.model.impl.Graph;

@Repository
@Transactional
public class ConceptTextDatabaseConnection implements IConceptTextDatabaseConnection {
	
	@Autowired
    private EntityManager em;
	
	@Override
	public List<ConceptText> allTextOfConcept(String conceptId) {
		// TODO Auto-generated method stub
		TypedQuery<ConceptText> query = em.createQuery("SELECT g from ConceptText g WHERE g.conceptId = :conceptId", ConceptText.class);
        query.setParameter("conceptId", conceptId);
        return query.getResultList();
	}

	@Override
	public void addText(ConceptText conceptText) {
		// TODO Auto-generated method stub
		em.persist(conceptText);
	}

	@Override
	public void update(String id, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		Long dbid = Long.parseLong(id);
		Object concept = em.find(ConceptText.class,dbid);
        if (concept != null) {
            em.remove(concept);
        }
	}

	@Override
	public List<ConceptText> showAllText() {
		// TODO Auto-generated method stub
		TypedQuery<ConceptText> query = em.createQuery("from ConceptText", ConceptText.class);
        return query.getResultList();
	}

}

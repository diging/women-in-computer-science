package edu.asu.diging.wic.core.conceptText.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;
import edu.asu.diging.wic.core.conceptText.repository.IConceptTextDatabaseConnection;

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
	public void update(String id, String text, String modifiedBy) {
		// TODO Auto-generated method stub
		TypedQuery<ConceptText> query = em.createQuery("SELECT g from ConceptText g WHERE g.id = :id", ConceptText.class);
        query.setParameter("id", Long.parseLong(id));
        ConceptText obj = query.getSingleResult();
        obj.setText(text);
        obj.setModifiedby(modifiedBy);
        obj.setModifiedOn(new Date().getTime()+"");
        addText(obj);
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

	@Override
	public ConceptText getConceptTextById(String id) {
		// TODO Auto-generated method stub
		TypedQuery<ConceptText> query = em.createQuery("SELECT g from ConceptText g WHERE g.id = :id", ConceptText.class);
		query.setParameter("id", Long.parseLong(id));
		ConceptText obj = query.getSingleResult();
		return obj;
	}


}

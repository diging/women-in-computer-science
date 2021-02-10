package edu.asu.diging.wic.core.conceptText.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptText.repository.IConceptTextDatabaseConnection;
import edu.asu.diging.wic.core.model.impl.ConceptText;

@Repository
@Transactional
public class ConceptTextDatabaseConnection implements IConceptTextDatabaseConnection {
	
	@Autowired
	private EntityManager em;

	@Override
	public void add(ConceptText conceptText) {

		em.persist(conceptText);
	}

	@Override
	public void update(ConceptText updatedForm, String modifiedBy) {

		TypedQuery<ConceptText> query = em.createQuery("SELECT g from ConceptText g WHERE g.id = :id", ConceptText.class);
        query.setParameter("id", updatedForm.getId());
        ConceptText obj = query.getSingleResult();
        obj.setText(updatedForm.getText());
        obj.setTitle(updatedForm.getTitle());
        obj.setAuthor(updatedForm.getAuthor());
        obj.setModifiedby(modifiedBy);
        obj.setModifiedOn(new Date().getTime()+"");
        add(obj);
	}

	@Override
	public void delete(String id) {

		Long dbid = Long.parseLong(id);
		Object concept = em.find(ConceptText.class,dbid);
        if (concept != null) {
            em.remove(concept);
        }
	}

	@Override
	public List<ConceptText> findAll() {

		TypedQuery<ConceptText> query = em.createQuery("from ConceptText", ConceptText.class);
        return query.getResultList();
	}

	@Override
	public ConceptText getConceptTextById(String id) {
		
		TypedQuery<ConceptText> query = em.createQuery("SELECT g from ConceptText g WHERE g.id = :id", ConceptText.class);
		query.setParameter("id", Long.parseLong(id));
		ConceptText obj = query.getSingleResult();
		return obj;
	}


}

package edu.asu.diging.wic.core.dataimport.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.diging.wic.core.dataimport.IImportedConceptDBConnection;
import edu.asu.diging.wic.core.model.IImportedConcept;
import edu.asu.diging.wic.core.model.impl.ImportedConcept;

@Component
public class ImportedConceptDBConnection implements IImportedConceptDBConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.dataimport.impl.IImportedConceptDBConnection#store(edu.asu.diging.wic.core.model.IImportedConcept)
     */
    @Override
    public void store(IImportedConcept concept) {
        sessionFactory.getCurrentSession().save(concept);
    }
    
    @Override
    public List<IImportedConcept> list() {
        TypedQuery<ImportedConcept> query = sessionFactory.getCurrentSession().createQuery("SELECT c from ImportedConcept c", ImportedConcept.class);
        return new ArrayList<IImportedConcept>(query.getResultList());
    }
    
    @Override
    public void remove(String id) {
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().find(ImportedConcept.class, id));
    }
    
    @Override
    public void updateImported(String id, String importer) {
        ImportedConcept concept = sessionFactory.getCurrentSession().find(ImportedConcept.class, id);
        concept.setImportedOn(OffsetDateTime.now());
        concept.setImportedBy(importer);
        sessionFactory.getCurrentSession().save(concept);
    }
    
    @Override
    public IImportedConcept get(String id) {
        return sessionFactory.getCurrentSession().find(ImportedConcept.class, id);
    }
}

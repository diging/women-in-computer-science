package edu.asu.diging.wic.core.dataimport.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.diging.wic.core.dataimport.IImportedConceptDBConnection;
import edu.asu.diging.wic.core.model.IImportedConcept;
import edu.asu.diging.wic.core.model.impl.ImportedConcept;
import edu.asu.diging.wic.core.repository.ImportedConceptRepository;

@Component
public class ImportedConceptDBConnection implements IImportedConceptDBConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
//    @Autowired
//    protected SessionFactory sessionFactory;
    
    @Autowired
    private ImportedConceptRepository conceptRepository;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.dataimport.impl.IImportedConceptDBConnection#store(edu.asu.diging.wic.core.model.IImportedConcept)
     */
    @Override
    public void store(IImportedConcept concept) {
        conceptRepository.save((ImportedConcept)concept);
    }
    
    @Override
    public List<IImportedConcept> list() {
//        TypedQuery<ImportedConcept> query = sessionFactory.getCurrentSession().createQuery("SELECT c from ImportedConcept c", ImportedConcept.class);
//        return new ArrayList<IImportedConcept>(query.getResultList());
        Iterable<ImportedConcept> concepts = conceptRepository.findAll();
        List<IImportedConcept> results = new ArrayList<>();
        concepts.forEach(c -> results.add(c));
        return results;
    }
    
    @Override
    public void remove(String id) {
        conceptRepository.deleteById(id);
    }
    
    @Override
    public void updateImported(String id, String importer) {
        Optional<ImportedConcept> optional = conceptRepository.findById(id);
        if (!optional.isPresent()) {
            return;
        }
        ImportedConcept concept = optional.get();
        concept.setImportedOn(OffsetDateTime.now());
        concept.setImportedBy(importer);
        conceptRepository.save(concept);
    }
    
    @Override
    public IImportedConcept get(String id) {
        Optional<ImportedConcept> optional = conceptRepository.findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        return optional.get();
    }
}

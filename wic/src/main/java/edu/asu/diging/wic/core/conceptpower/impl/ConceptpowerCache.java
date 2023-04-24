package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.conceptpower.IASyncConceptUpdater;
import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.wic.core.conceptpower.repository.ConceptRepository;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

@Service
public class ConceptpowerCache implements IConceptpowerCache {
	
    @Autowired
    private ConceptRepository conceptRepository;
	
    @Autowired
    private IConceptpowerConnector connector;
	
    @Autowired
    private IASyncConceptUpdater conceptUpdater;
    
    @Override
    public IConcept getConceptById(String id) {
		
        IConcept concept = conceptRepository.getConcept(id);
        if(concept != null) {
            conceptUpdater.updateConcept(id);
            return concept;
        }
		
        concept = connector.getConcept(id);
        if(concept != null) {
            conceptRepository.save(concept);
        }
        return conceptRepository.getConcept(concept.getId());  
    }
    
    @Override
    public IConcept getConceptByUri(String uri) {
        IConcept concept = conceptRepository.findByUri(uri);
        if(concept != null) {
            conceptUpdater.updateConcept(concept.getId());
            return concept;
        }
        
        concept = connector.getConcept(uri);
        if(concept != null) {
            conceptRepository.save(concept);
        }
        return concept; 
    }

    @Override
    public List<ConceptType> getAllConceptTypes() {
        return conceptRepository.getAllConceptTypes();
    }
}
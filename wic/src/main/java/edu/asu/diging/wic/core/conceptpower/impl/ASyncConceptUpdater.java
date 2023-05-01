package edu.asu.diging.wic.core.conceptpower.impl;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.conceptpower.IASyncConceptUpdater;
import edu.asu.diging.wic.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.repository.ConceptRepository;

@Service
@PropertySource("classpath:config.properties")
public class ASyncConceptUpdater implements IASyncConceptUpdater {
	
    @Autowired
    private ConceptRepository conceptRepository;
	
    @Autowired
    private IConceptpowerConnector connector;
    
    @Value("${concepts.refresh.time}")
    private String updateDuration;
    
    @Override
    @Async
    public void updateConcept(String id) {
        IConcept storedConcept = conceptRepository.getConcept(id);
        if (storedConcept.getLastUpdated() != null && storedConcept.getLastUpdated().plus(Duration.parse(updateDuration)).isAfter(OffsetDateTime.now())) {
            // only update a concept every 2 days
            return;
        }
        
        IConcept concept = connector.getConcept(id);
        if(concept != null) {
            conceptRepository.save(concept);
            updateAlternativeIdConcepts(concept);
        }
    }
    
    private void updateAlternativeIdConcepts(IConcept concept) {
        List<String> altIds = new ArrayList<>(concept.getAlternativeUris());
        altIds.forEach(aid -> {
            if (!aid.trim().isEmpty()) {
                String id = aid.substring(aid.lastIndexOf("/")+1);
                // apparently there is bad data in Conceptpower
                if (id.isEmpty()) {
                    return;
                }
                concept.setUri(aid);
                concept.setId(id);
                concept.setAlternativeUris(new ArrayList<>(concept.getAlternativeUris()));
                concept.setEqualTo(new ArrayList<>(concept.getEqualTo()));
                concept.setSimilarTo(new ArrayList<>(concept.getSimilarTo()));
                concept.setWordnetIds(new ArrayList<>(concept.getWordnetIds()));
                conceptRepository.save(concept);
            }
        });
    }
}
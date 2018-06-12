package edu.asu.diging.wic.core.conceptpower.impl;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.conceptpower.IASyncConceptUpdater;
import edu.asu.diging.wic.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.wic.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.wic.core.model.IConcept;

@Service
@PropertySource("classpath:config.properties")
public class ASyncConceptUpdater implements IASyncConceptUpdater {
	
    @Autowired
    private IConceptDatabaseConnection conceptDB;
	
    @Autowired
    private IConceptpowerConnector connector;
    
    @Value("${concepts.refresh.time}")
    private String updateDuration;
    
    @Override
    @Async
    public void updateConcept(String id) {
        IConcept storedConcept = conceptDB.getConcept(id);
        if (storedConcept.getLastUpdated() != null && storedConcept.getLastUpdated().plus(Duration.parse(updateDuration)).isAfter(OffsetDateTime.now())) {
            // only update a concept every 2 days
            return;
        }
        
        IConcept concept = connector.getConcept(id);
        if(concept != null) {
            conceptDB.createOrUpdate(concept);
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
                conceptDB.createOrUpdate(concept);
            }
        });
    }
}
package edu.asu.diging.wic.core.dataimport.impl;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.dataimport.IDataImporter;
import edu.asu.diging.wic.core.dataimport.IImportedConceptDBConnection;
import edu.asu.diging.wic.core.dataimport.ITransactionalImportManager;
import edu.asu.diging.wic.core.dataimport.model.ProgressStatus;
import edu.asu.diging.wic.core.graphs.IGraphDBConnection;
import edu.asu.diging.wic.core.graphs.IGraphManager;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IImportedConcept;
import edu.asu.diging.wic.core.model.impl.Graph;
import edu.asu.diging.wic.core.model.impl.ImportedConcept;
import edu.asu.diging.wic.core.repository.GraphRepository;

@Service
public class DataImporter implements IDataImporter {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IConceptpowerCache conceptpower;
     
    @Autowired
    private IGraphManager graphManager;
    
    @Autowired
    private GraphRepository graphRepository;
    
    @Autowired
    private IImportedConceptDBConnection importedConceptDb;
    
    @Autowired
    private ITransactionalImportManager phaseManager;
    
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.dataimport.impl.IDataImporter#importPerson(java.lang.String, java.lang.String)
     */
    @Override
    @Async
    @Transactional
    public void importPerson(String conceptId, String importer, String progressId) {
        IConcept concept = conceptpower.getConceptById(conceptId);
        
        logger.info("Retrieving graph for " + concept.getUri());
        try {
            graphManager.transformGraph(concept.getUri(), progressId);
        } catch (IOException e1) {
            logger.error("Could not start transformation.", e1);
            phaseManager.updateProgress(progressId, ProgressStatus.FAILED, ZonedDateTime.now());
            return;
        }
        Graph graph = null;
        while (graph == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                logger.error("Could not sleep.", e);
                break;
            }
            graph = graphManager.getTransfomationResult(concept.getUri());
        }
        
        String phaseTitle = "Storing retrieved graph.";
        phaseManager.addNewPhase(progressId, phaseTitle, ProgressStatus.STARTED);
        
        // remove previously stored graphs before adding updated one
        graphRepository.removeGraphs(concept.getUri());
        if (graph != null) {
            graph.setConceptUri(concept.getUri());
            graphRepository.save(graph);
        }
        
        phaseManager.updatePhase(progressId, phaseTitle, ProgressStatus.DONE);
        
        IImportedConcept importedConcept = importedConceptDb.get(conceptId);
        if (importedConcept == null) {
            importedConcept = new ImportedConcept(conceptId, OffsetDateTime.now(), importer);
            importedConceptDb.store(importedConcept);
        } else {
            importedConceptDb.updateImported(conceptId, importer);
        }
        
        // FIXME: let transformGraph return status and use that instead
        if (phaseManager.getProgress(progressId).getStatus() != ProgressStatus.FAILED) {
            phaseManager.updateProgress(progressId, ProgressStatus.DONE, ZonedDateTime.now());
        }
    }
}

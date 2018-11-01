package edu.asu.diging.wic.core.dataimport.impl;

import java.time.ZonedDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.dataimport.ITransactionalImportManager;
import edu.asu.diging.wic.core.dataimport.db.impl.ImportProgressDbConnection;
import edu.asu.diging.wic.core.dataimport.model.ImportPhase;
import edu.asu.diging.wic.core.dataimport.model.ImportProgress;
import edu.asu.diging.wic.core.dataimport.model.ProgressStatus;

@Service
@Transactional
public class TransactionalImportManager implements ITransactionalImportManager {

    @Autowired
    private ImportProgressDbConnection importDbConnector;
    
    @Override
    public String createNewProgress(String conceptId) {
        ImportProgress progress = new ImportProgress();
        progress.setConceptId(conceptId);
        progress.setStatus(ProgressStatus.STARTED);
        progress.setStartDate(ZonedDateTime.now());
        return importDbConnector.store(progress);
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.dataimport.impl.IImportPhaseManager#addNewPhase(java.lang.String, java.lang.String, edu.asu.diging.wic.core.dataimport.model.ProgressStatus)
     */
    @Override
    public void addNewPhase(String progressId, String phaseTitle, ProgressStatus status) {
        ImportProgress progress = importDbConnector.get(progressId);

        ImportPhase phase = new ImportPhase();
        phase.setTitle(phaseTitle);
        phase.setStatus(status);
        progress.getPhases().add(phase);
        importDbConnector.update(progress);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.dataimport.impl.IImportPhaseManager#updatePhase(java.lang.String, java.lang.String, edu.asu.diging.wic.core.dataimport.model.ProgressStatus)
     */
    @Override
    public void updatePhase(String progressId, String phaseTitle, ProgressStatus status) {
        ImportProgress progress = importDbConnector.get(progressId);
        for (ImportPhase phase : progress.getPhases()) {
            // ugly but ahh well
            if (phase.getTitle().equals(phaseTitle)) {
                phase.setStatus(status);
                importDbConnector.update(progress);
                break;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.dataimport.impl.IImportPhaseManager#updatePhaseAndProgress(java.lang.String, java.lang.String, edu.asu.diging.wic.core.dataimport.model.ProgressStatus, java.time.ZonedDateTime)
     */
    @Override
    public void updatePhaseAndProgress(String progressId, String phaseTitle, ProgressStatus status) {
        ImportProgress progress = importDbConnector.get(progressId);
        progress.setStatus(status);
        for (ImportPhase phase : progress.getPhases()) {
            // ugly but ahh well
            if (phase.getTitle().equals(phaseTitle)) {
                phase.setStatus(status);
                importDbConnector.update(progress);
                break;
            }
        }
    }
    
    @Override
    public void updateProgress(String progressId, ProgressStatus status, ZonedDateTime endTime) {
        ImportProgress progress = importDbConnector.get(progressId);
        progress.setEndDate(endTime);
        progress.setStatus(status);
        importDbConnector.update(progress);
    }
    
    @Override
    public ImportProgress getProgress(String id) {
        ImportProgress progress = importDbConnector.get(id);
        // load phases
        progress.getPhases().size();
        return progress;
    }
}

package edu.asu.diging.wic.core.dataimport.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.dataimport.ITransactionalImportManager;
import edu.asu.diging.wic.core.dataimport.model.ImportPhase;
import edu.asu.diging.wic.core.dataimport.model.ImportProgress;
import edu.asu.diging.wic.core.dataimport.model.ProgressStatus;
import edu.asu.diging.wic.core.repository.ImportProgressRepository;

@Service
@Transactional
public class TransactionalImportManager implements ITransactionalImportManager {

    @Autowired
    private ImportProgressRepository progressRepository;

    @Override
    public String createNewProgress(String conceptId) {
        ImportProgress progress = new ImportProgress();
        progress.setConceptId(conceptId);
        progress.setStatus(ProgressStatus.STARTED);
        progress.setStartDate(ZonedDateTime.now());
        progress = progressRepository.save(progress);
        return progress.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.wic.core.dataimport.impl.IImportPhaseManager#addNewPhase(
     * java.lang.String, java.lang.String,
     * edu.asu.diging.wic.core.dataimport.model.ProgressStatus)
     */
    @Override
    public void addNewPhase(String progressId, String phaseTitle,
            ProgressStatus status) {
        Optional<ImportProgress> progressOptional = progressRepository
                .findById(progressId);
        if (!progressOptional.isPresent()) {
            return;
        }
        ImportProgress progress = progressOptional.get();

        ImportPhase phase = new ImportPhase();
        phase.setTitle(phaseTitle);
        phase.setStatus(status);
        progress.getPhases().add(phase);
        progressRepository.save(progress);
        progressRepository.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.wic.core.dataimport.impl.IImportPhaseManager#updatePhase(
     * java.lang.String, java.lang.String,
     * edu.asu.diging.wic.core.dataimport.model.ProgressStatus)
     */
    @Override
    public void updatePhase(String progressId, String phaseTitle,
            ProgressStatus status) {
        Optional<ImportProgress> progressOptional = progressRepository
                .findById(progressId);
        if (!progressOptional.isPresent()) {
            return;
        }
        ImportProgress progress = progressOptional.get();
        for (ImportPhase phase : progress.getPhases()) {
            // ugly but ahh well
            if (phase.getTitle().equals(phaseTitle)) {
                phase.setStatus(status);
                progressRepository.save(progress);
                break;
            }
        }
        progressRepository.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.wic.core.dataimport.impl.IImportPhaseManager#
     * updatePhaseAndProgress(java.lang.String, java.lang.String,
     * edu.asu.diging.wic.core.dataimport.model.ProgressStatus,
     * java.time.ZonedDateTime)
     */
    @Override
    public void updatePhaseAndProgress(String progressId, String phaseTitle,
            ProgressStatus status) {
        Optional<ImportProgress> progressOptional = progressRepository
                .findById(progressId);
        if (!progressOptional.isPresent()) {
            return;
        }
        ImportProgress progress = progressOptional.get();

        progress.setStatus(status);
        for (ImportPhase phase : progress.getPhases()) {
            // ugly but ahh well
            if (phase.getTitle().equals(phaseTitle)) {
                phase.setStatus(status);
                progressRepository.save(progress);
                break;
            }
        }
        progressRepository.flush();
    }

    @Override
    public void updateProgress(String progressId, ProgressStatus status,
            ZonedDateTime endTime) {
        Optional<ImportProgress> progressOptional = progressRepository
                .findById(progressId);
        if (!progressOptional.isPresent()) {
            return;
        }
        ImportProgress progress = progressOptional.get();

        progress.setEndDate(endTime);
        progress.setStatus(status);
        progressRepository.save(progress);
    }

    @Override
    public ImportProgress getProgress(String id) {
        Optional<ImportProgress> progressOptional = progressRepository
                .findById(id);
        if (!progressOptional.isPresent()) {
            return null;
        }
        ImportProgress progress = progressOptional.get();
        // load phases
        progress.getPhases().size();
        return progress;
    }

    @Override
    public List<ImportProgress> getAllProgresses() {
        Iterable<ImportProgress> all = progressRepository.findAll();
        List<ImportProgress> results = new ArrayList<>();
        all.forEach(i -> results.add(i));
        return results;
    }

}

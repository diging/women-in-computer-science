package edu.asu.diging.wic.core.dataimport;

import java.time.ZonedDateTime;
import java.util.List;

import edu.asu.diging.wic.core.dataimport.model.ImportProgress;
import edu.asu.diging.wic.core.dataimport.model.ProgressStatus;

public interface ITransactionalImportManager {

    void addNewPhase(String progressId, String phaseTitle,
            ProgressStatus status);

    void updatePhase(String progressId, String phaseTitle,
            ProgressStatus status);

    void updatePhaseAndProgress(String progressId, String phaseTitle,
            ProgressStatus status);

    void updateProgress(String progressId, ProgressStatus status, ZonedDateTime endTime);

    String createNewProgress(String conceptId);

    ImportProgress getProgress(String id);

    List<ImportProgress> getAllProgresses();

}
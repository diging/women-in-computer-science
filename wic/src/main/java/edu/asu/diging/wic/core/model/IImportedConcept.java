package edu.asu.diging.wic.core.model;

import java.time.OffsetDateTime;

public interface IImportedConcept {

    String getConceptId();

    void setConceptId(String conceptId);

    OffsetDateTime getImportedOn();

    void setImportedOn(OffsetDateTime importedOn);

    String getImportedBy();

    void setImportedBy(String importedBy);

}
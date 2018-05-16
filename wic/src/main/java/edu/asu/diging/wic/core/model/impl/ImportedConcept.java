package edu.asu.diging.wic.core.model.impl;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import edu.asu.diging.wic.core.model.IImportedConcept;

@Entity
public class ImportedConcept implements IImportedConcept {

    @Id
    private String conceptId;
    private OffsetDateTime importedOn;
    private String importedBy;
    
    public ImportedConcept() {}
    
    public ImportedConcept(String conceptId, OffsetDateTime importedOn,
            String importedBy) {
        super();
        this.conceptId = conceptId;
        this.importedOn = importedOn;
        this.importedBy = importedBy;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.model.impl.IImportedConcept#getConceptId()
     */
    @Override
    public String getConceptId() {
        return conceptId;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.model.impl.IImportedConcept#setConceptId(java.lang.String)
     */
    @Override
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.model.impl.IImportedConcept#getImportedOn()
     */
    @Override
    public OffsetDateTime getImportedOn() {
        return importedOn;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.model.impl.IImportedConcept#setImportedOn(java.time.OffsetDateTime)
     */
    @Override
    public void setImportedOn(OffsetDateTime importedOn) {
        this.importedOn = importedOn;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.model.impl.IImportedConcept#getImportedBy()
     */
    @Override
    public String getImportedBy() {
        return importedBy;
    }
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.model.impl.IImportedConcept#setImportedBy(java.lang.String)
     */
    @Override
    public void setImportedBy(String importedBy) {
        this.importedBy = importedBy;
    }
    
}

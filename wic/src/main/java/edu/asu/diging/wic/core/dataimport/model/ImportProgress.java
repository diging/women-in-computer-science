package edu.asu.diging.wic.core.dataimport.model;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class ImportProgress {

    @Id
    @GeneratedValue(generator = "import_id_generator")
    @GenericGenerator(name = "import_id_generator", parameters = @Parameter(name = "prefix", value = "IM"), strategy = "edu.asu.diging.wic.core.db.IdGenerator")
    private String id;
    
    @Enumerated(EnumType.STRING)
    private ProgressStatus status;
    private String conceptId;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "import_id")
    private List<ImportPhase> phases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProgressStatus getStatus() {
        return status;
    }

    public void setStatus(ProgressStatus status) {
        this.status = status;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public List<ImportPhase> getPhases() {
        return phases;
    }

    public void setPhases(List<ImportPhase> phases) {
        this.phases = phases;
    }
}

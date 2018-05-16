package edu.asu.diging.wic.core.conceptpower;

import edu.asu.diging.wic.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.wic.core.model.IConcept;

public interface IConceptMapper {

    IConcept mapConceptpowerConceptToConcept(ConceptpowerConcept conceptpowerConcept);
}

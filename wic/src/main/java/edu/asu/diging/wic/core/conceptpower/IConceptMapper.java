package edu.asu.diging.wic.core.conceptpower;

import edu.asu.diging.wic.core.conceptpower.impl.ConceptEntry;
import edu.asu.diging.wic.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.wic.core.model.IConcept;

public interface IConceptMapper {

    /**
     * Maps the conceptpower entry received from JSON type response to the concept object
     * @param conceptpowerConcept received conceptpower entry
     * @return the mapped object
     */
    IConcept mapConceptpowerConceptToConcept(ConceptpowerConcept conceptpowerConcept);
    
    /**
     * Maps the conceptpower entry received from XML type response to the concept object
     * @param conceptEntry received conceptpower entry
     * @return the mapped object
     */
    IConcept mapConceptpowerConceptToConcept(ConceptEntry conceptEntry);
}

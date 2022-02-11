package edu.asu.diging.wic.core.conceptpower;

import edu.asu.diging.wic.core.model.IConcept;

public interface IConceptMapper {
    
    /**
     * Maps the Conceptpower entry received to the concept object
     * @param conceptEntry received conceptpower entry
     * @return the mapped object
     */
    IConcept mapConceptpowerConceptToConcept(IConceptEntry conceptEntry);
}

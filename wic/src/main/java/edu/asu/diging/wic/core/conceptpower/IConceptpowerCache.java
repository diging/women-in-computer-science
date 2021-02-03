package edu.asu.diging.wic.core.conceptpower;

import java.util.List;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

public interface IConceptpowerCache {

    IConcept getConceptById(String uri);

    IConcept getConceptByUri(String uri);
    
    List<ConceptType> getAllConceptType();
}

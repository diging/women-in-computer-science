package edu.asu.diging.wic.core.conceptpower;

import edu.asu.diging.wic.core.model.IConcept;

public interface IConceptpowerCache {

    IConcept getConceptById(String uri);

    IConcept getConceptByUri(String uri);
}

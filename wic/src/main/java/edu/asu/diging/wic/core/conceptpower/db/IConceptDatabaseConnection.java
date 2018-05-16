package edu.asu.diging.wic.core.conceptpower.db;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IConceptType;

public interface IConceptDatabaseConnection {

    IConcept getConcept(String uri);
	
    void createOrUpdate(IConcept concept);

    void deleteConcept(String uri);

    IConceptType getType(String uri);

    IConcept getConceptByUri(String uri);

}

package edu.asu.diging.wic.core.conceptpower;

import java.util.List;

import edu.asu.diging.wic.core.model.IConcept;

public interface IConceptpowerConnector {

    IConcept getConcept(String id);

    List<IConcept> search(String searchTerm);
}
package edu.asu.diging.wic.core.service;

import java.util.List;

import edu.asu.diging.wic.core.model.IConcept;

public interface IStatementService {

    List<IConcept> getImportedConcepts();

}
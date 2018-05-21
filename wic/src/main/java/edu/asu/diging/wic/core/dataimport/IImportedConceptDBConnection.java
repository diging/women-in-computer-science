package edu.asu.diging.wic.core.dataimport;

import java.util.List;

import edu.asu.diging.wic.core.model.IImportedConcept;

public interface IImportedConceptDBConnection {

    void store(IImportedConcept concept);

    List<IImportedConcept> list();

    void updateImported(String id, String importer);

    void remove(String id);

    IImportedConcept get(String id);

}
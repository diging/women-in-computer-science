package edu.asu.diging.wic.core.dataimport;

import edu.asu.diging.wic.core.dataimport.model.ImportProgress;

public interface IImportCoordinator {

    String startImport(String conceptId, String importer);

    ImportProgress getProgress(String id);

}
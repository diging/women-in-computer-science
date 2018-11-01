package edu.asu.diging.wic.core.dataimport.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.dataimport.IDataImporter;
import edu.asu.diging.wic.core.dataimport.IImportManager;
import edu.asu.diging.wic.core.dataimport.IImportPhaseManager;

@Service
public class ImportManager implements IImportManager {

    @Autowired
    private IDataImporter dataImporter;

    @Autowired
    private IImportPhaseManager importManager;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.diging.wic.core.dataimport.impl.IImportManager#startImport(java.
     * lang.String, java.lang.String)
     */
    @Override
    public String startImport(String conceptId, String importer) {
        String id = importManager.createNewProgress(conceptId);
        dataImporter.importPerson(conceptId, importer, id);
        return id;
    }
}

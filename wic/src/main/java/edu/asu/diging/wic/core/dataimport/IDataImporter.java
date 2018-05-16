package edu.asu.diging.wic.core.dataimport;

import org.springframework.scheduling.annotation.Async;

public interface IDataImporter {

    void importPerson(String conceptId, String importer);

}
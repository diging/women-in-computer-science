package edu.asu.diging.wic.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.asu.diging.wic.core.model.impl.ImportedConcept;

public interface ImportedConceptRepository extends PagingAndSortingRepository<ImportedConcept, String> {

}

package edu.asu.diging.wic.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.asu.diging.wic.core.dataimport.model.ImportProgress;

public interface ImportProgressRepository extends PagingAndSortingRepository<ImportProgress, String>, JpaRepository<ImportProgress, String> {

}

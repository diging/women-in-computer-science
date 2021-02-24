package edu.asu.diging.wic.core.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.impl.ConceptText;

@Repository
@JaversSpringDataAuditable
public interface ConceptTextDatabaseRepository extends PagingAndSortingRepository<ConceptText, Long> {



}

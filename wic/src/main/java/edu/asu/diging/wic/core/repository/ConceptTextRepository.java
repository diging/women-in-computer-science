package edu.asu.diging.wic.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.impl.ConceptText;

@Repository
public interface ConceptTextRepository extends PagingAndSortingRepository<ConceptText, Long> {

}

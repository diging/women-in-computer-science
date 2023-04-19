package edu.asu.diging.wic.core.conceptpower.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IConceptType;
import edu.asu.diging.wic.core.model.impl.Concept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

@Repository
public interface ConceptRepository extends PagingAndSortingRepository<Concept, String> {
    public IConcept findByUri(String uri);

    public void createOrUpdate(IConcept concept);

    public void deleteConcept(String uri);

    public IConceptType getType(String id);

    public List<ConceptType> getAllConceptTypes();

    public Page<IConcept> findByTypeId(String typeId, Pageable pageable);
}

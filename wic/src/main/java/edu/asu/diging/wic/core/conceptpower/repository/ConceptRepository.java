package edu.asu.diging.wic.core.conceptpower.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IConceptType;
import edu.asu.diging.wic.core.model.impl.Concept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

@Repository
public interface ConceptRepository extends PagingAndSortingRepository<Concept, String> {
    
    @Query("SELECT c FROM Concept c WHERE c.id = :id")
    public IConcept getConcept(@Param("id") String id);
    
    public IConcept findByUri(String uri);

    public void save(IConcept concept);

    public void deleteByUri(String uri);

    @Query("SELECT t FROM ConceptType t WHERE t.id=:id")
    public IConceptType getType(@Param("id") String id);

    @Query("SELECT ct FROM ConceptType ct")
    public List<ConceptType> getAllConceptTypes();

    public Page<IConcept> findByTypeId(String typeId, Pageable pageable);
}

package edu.asu.diging.wic.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import edu.asu.diging.wic.core.model.IConceptType;
import edu.asu.diging.wic.core.model.impl.ConceptType;

/**
 * Repository interface for managing ConceptType entities.
 */
public interface ConceptTypeRepository extends PagingAndSortingRepository<ConceptType, String> {

    /**
     * Retrieve a ConceptType object by its ID.
     * 
     * @param id the ID of the ConceptType to retrieve
     * @return the ConceptType object with the given ID, or null if not found
     */
    @Query("SELECT t FROM ConceptType t WHERE t.id=:id")
    public IConceptType getType(@Param("id") String id);

    /**
     * Retrieve all ConceptType objects from the database.
     * 
     * @return a List of all ConceptType objects in the database
     */
    public List<ConceptType> findAll();

}

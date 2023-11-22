package edu.asu.diging.wic.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.impl.Concept;

/**
 * Repository interface for managing Concept entities.
 */
@Repository
public interface ConceptRepository extends PagingAndSortingRepository<Concept, String> {

	/**
	 * Retrieve a Concept object by its ID.
	 * 
	 * @param id the ID of the Concept to retrieve
	 * @return the Concept object with the given ID, or null if not found
	 */
	@Query("SELECT c FROM Concept c WHERE c.id = :id")
	public IConcept getConcept(@Param("id") String id);

	/**
	 * Retrieve a Concept object by its URI.
	 * 
	 * @param uri the URI of the Concept to retrieve
	 * @return the Concept object with the given URI, or null if not found
	 */
	public IConcept findByUri(String uri);

	/**
	 * Save a Concept object to the database.
	 * 
	 * @param concept the Concept object to save
	 */
	public void save(IConcept concept);

	/**
	 * Delete a Concept object from the database by its URI.
	 * 
	 * @param uri the URI of the Concept to delete
	 */
	public void deleteByUri(String uri);

	/**
	 * Retrieve a page of Concept objects that belong to a given type.
	 * 
	 * @param typeId   the ID of the ConceptType that the Concepts belong to
	 * @param pageable the Pageable object that specifies the page number, size, and
	 *                 sorting order
	 * @return a Page of Concept objects that belong to the specified type
	 */
	public Page<IConcept> findByTypeId(String typeId, Pageable pageable);

}

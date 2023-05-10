package edu.asu.diging.wic.core.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.impl.Edge;

/**
 * Repository interface for managing Edge objects
 */
@Repository
public interface EdgeRepository extends PagingAndSortingRepository<Edge, String> {

	/**
	 * Retrieves a list of Edge objects based on a given list of source node URIs.
	 * 
	 * @param alternativeUris the list of source node URIs to search for
	 * @return a list of Edge objects that have a source node with one of the given
	 *         URIs
	 */
	public List<Edge> findBySourceNodeUriIn(@Param("alternativeUris") List<String> alternativeUris);

}

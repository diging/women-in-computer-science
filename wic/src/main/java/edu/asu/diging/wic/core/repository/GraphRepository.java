package edu.asu.diging.wic.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.model.impl.Graph;

/**
 * Repository interface for managing Graph Entities
 */
@Repository
public interface GraphRepository extends PagingAndSortingRepository<Graph, String> {

	/**
	 * Retrieves a list of Graph objects based on a given concept URI.
	 * 
	 * @param uri the concept URI to search for
	 * @return a list of Graph objects that match the given concept URI
	 */
	@Query("SELECT g from Graph g WHERE g.conceptUri = :uri")
	public List<Graph> getGraphs(@Param("uri") String uri);

	/**
	 * Saves a Graph object to the database.
	 * 
	 * @param graph the Graph object to save
	 * @return the saved Graph object
	 */
	public Graph save(Graph graph);

	/**
	 * Retrieves a list of all concept URIs from Graph objects.
	 * 
	 * @return a list of all concept URIs from Graph objects
	 */
	@Query("SELECT g.conceptUri FROM Graph g")
	public List<String> getAllPersons();

	/**
	 * Deletes Graph objects based on a given concept URI.
	 * 
	 * @param uri the concept URI to search for and delete
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Graph g WHERE g.conceptUri = :uri")
	public void removeGraphs(@Param("uri") String uri);

}

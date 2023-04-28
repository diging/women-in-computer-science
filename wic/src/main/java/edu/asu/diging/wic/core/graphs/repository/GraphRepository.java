package edu.asu.diging.wic.core.graphs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.model.impl.Edge;
import edu.asu.diging.wic.core.model.impl.Graph;

@Repository
public interface GraphRepository extends PagingAndSortingRepository<Graph, String> {

    @Query("SELECT g from Graph g WHERE g.conceptUri = :uri")
    public List<Graph> getGraphs(@Param("uri") String uri);

    public Graph save(Graph graph);

    @Query("SELECT e FROM Edge e WHERE e.sourceNode.uri IN "
            + "(SELECT alternativeUris FROM ConceptpowerAlternativeUri c WHERE c.id = :uri) "
            + "OR e.targetNode.uri IN "
            + "(SELECT alternativeUris FROM ConceptpowerAlternativeUri c WHERE c.id = :uri)")
    public List<Edge> getEdges(@Param("uri") String uri);

    @Query("SELECT g.conceptUri FROM Graph g")
    public List<String> getAllPersons();

    @Transactional
    @Modifying
    @Query("DELETE FROM Graph g WHERE g.conceptUri = :uri")
    public void removeGraphs(@Param("uri") String uri);

    @Query("SELECT a.alternativeUris FROM ConceptpowerAlternativeUri a WHERE a.id = :conceptUri")
    public List<String> getAlternativeUris(@Param("conceptUri") String conceptUri);

}

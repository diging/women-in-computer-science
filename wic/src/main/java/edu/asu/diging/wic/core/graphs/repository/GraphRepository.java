package edu.asu.diging.wic.core.graphs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.asu.diging.wic.core.model.impl.Graph;

@Repository
public interface GraphRepository extends PagingAndSortingRepository<Graph, String> {
    
    @Query("SELECT g from Graph g WHERE g.conceptUri = :uri")
    public List<Graph> getGraphs(@Param("uri") String uri);
    
    public Graph save(Graph graph);
    
    

}

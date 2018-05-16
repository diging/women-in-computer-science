package edu.asu.diging.wic.core.graphs;

import java.util.List;

import edu.asu.diging.wic.core.model.impl.Edge;
import edu.asu.diging.wic.core.model.impl.Graph;

public interface IGraphDBConnection {

    void store(Graph graph);

    List<Graph> getGraphs(String conceptUri);
    
    List<Edge> getEdges(String conceptUri);

    void removeGraphs(String conceptUri);

    List<String> getAllPersons();

}
package edu.asu.diging.wic.core.graphs.db.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.graphs.IGraphDBConnection;
import edu.asu.diging.wic.core.model.impl.Edge;
import edu.asu.diging.wic.core.model.impl.Graph;

@Transactional
@Service
public class GraphDBConnection implements IGraphDBConnection {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private EntityManager em;

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.db.impl.IGraphDBConnection#store(edu.asu.diging.grazer.core.model.impl.Graph)
     */
    @Override
    public void store(Graph graph) {
        em.persist(graph);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.db.impl.IGraphDBConnection#getGraphs(java.lang.String)
     */
    @Override
    public List<Graph> getGraphs(String conceptUri) {
        TypedQuery<Graph> query = em.createQuery("SELECT g from Graph g WHERE g.conceptUri = :uri", Graph.class);
        query.setParameter("uri", conceptUri);
        return query.getResultList();
    }
    
    @Override
    public List<Edge> getEdges(String conceptUri) {
        
        Query query = em.createNativeQuery("select alternativeUris from tbl_conceptpower_alternativeuris where id = '" + conceptUri + "'");
        List<String> uris = query.getResultList();
        
        CriteriaQuery<Edge> cQuery = em.getCriteriaBuilder().createQuery(Edge.class);
        Root<Edge> root = cQuery.from(Edge.class);
        cQuery.select(root);
        
        String edgeQueryString = "SELECT e FROM Edge e WHERE e.sourceNode.uri IN :uris OR e.targetNode.uri IN :uris";
        TypedQuery<Edge> edgeQuery = em.createQuery(edgeQueryString, Edge.class);
        edgeQuery.setParameter("uris", uris);
        return edgeQuery.getResultList();
    }
    
    @Override
    public List<String> getAllPersons() {
        Query query = em.createQuery("SELECT g.conceptUri from Graph g");
        return query.getResultList();
    }
    
    @Override
    public void removeGraphs(String conceptUri) {
        List<Graph> graphs = getGraphs(conceptUri);
        if (graphs == null) {
            return;
        }
        for (Graph graph : graphs) {
            em.remove(graph);
        }
    }
}
//insert into

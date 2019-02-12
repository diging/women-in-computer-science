package edu.asu.diging.wic.core.graphs.db.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
    
//    @Autowired
//    protected SessionFactory sessionFactory;
    
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
        
        //Criteria c = em.createCriteria(Edge.class, "edge");
        
        CriteriaQuery<Edge> cQuery = em.getCriteriaBuilder().createQuery(Edge.class);
        Root<Edge> root = cQuery.from(Edge.class);
        cQuery.select(root);
        
        String edgeQueryString = "SELECT e FROM Edge e WHERE e.sourceNode.uri IN :uris OR e.targetNode.uri IN :uris";
        Query edgeQuery = em.createQuery(edgeQueryString);
        edgeQuery.setParameter("uris", uris);
        return edgeQuery.getResultList();
        
//        c.createAlias("edge.sourceNode", "sourceNode");
//        c.createAlias("edge.targetNode", "targetNode");
//        c.add(Restrictions.disjunction()
//                .add(Restrictions.in("targetNode.uri", uris))
//                .add(Restrictions.in("sourceNode.uri", uris)));
//        ProjectionList projList = Projections.projectionList()
//                .add(Projections.property("id"), "id")
//                .add(Projections.property("source"), "source")
//                .add(Projections.property("target"), "target")
//                .add(Projections.property("label"), "label")
//                .add(Projections.property("concept"), "concept")
//                .add(Projections.property("sourceUri"), "sourceUri")
//                .add(Projections.property("startTime"), "startTime")
//                .add(Projections.property("endTime"), "endTime")
//                .add(Projections.property("occurred"), "occurred")
//                .add(Projections.property("sourceNode"), "sourceNode")
//                .add(Projections.property("targetNode"), "targetNode");
//        c.setProjection(Projections.distinct(projList));
//        c.setResultTransformer(Transformers.aliasToBean(Edge.class));
//        return c.list();
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

package edu.asu.diging.wic.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.graphs.IGraphDBConnection;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.impl.Edge;
import edu.asu.diging.wic.core.model.impl.Graph;
import edu.asu.diging.wic.core.model.impl.Node;
import edu.asu.diging.wic.core.quadriga.impl.TransformationResponse;
import edu.asu.diging.wic.core.service.IStatementService;
import edu.asu.diging.wic.web.cytoscape.Data;
import edu.asu.diging.wic.web.cytoscape.EdgeData;
import edu.asu.diging.wic.web.cytoscape.GraphElement;

@Controller
@PropertySource("classpath:/config.properties")
public class HomeController {
    
    @Autowired
    private IStatementService statementService;
    
    @Autowired
    private IGraphDBConnection graphDbConnection;
    
    @Autowired
    private IConceptpowerCache conceptCache;
    
    @Value("${general.node.color}")
    private String generalNodeColor;
    
    @Value("${person.node.color}")
    private String personNodeColor;
    
    @Value("${institution.node.color}")
    private String institutionNodeColor;
    
    @Value("${location.node.color}")
    private String locationNodeColor;
    
    @Value("${concepts.type.person}")
    private String personTypeId;
    
    @Value("${concepts.type.institution}")
    private String institutionTypeId;
    
    @Value("${concepts.type.location}")
    private String locationTypeId;

    @RequestMapping(value = "/")
    public String home(Model model) {
        List<IConcept> concepts = statementService.getImportedConcepts();
        model.addAttribute("concepts", concepts);
        
        return "home";
    }
    
    @RequestMapping(value = "/network")
    public ResponseEntity<Collection<GraphElement>> getPersonNetwork() {
    	
        List<String> uris = graphDbConnection.getAllPersons();
        ConcurrentHashMap<String, GraphElement> elements = new ConcurrentHashMap<>();
        
        ExecutorService executorService  = Executors.newCachedThreadPool();
        
        List<Callable<Void>> todoGraph = new ArrayList<Callable<Void>>();
        for (String uri : uris) {
            List<Graph> graphs = graphDbConnection.getGraphs(uri);
            for (Graph graph : graphs) {
            	todoGraph.add(new Callable<Void>() {
            	    public Void call() throws Exception {
            	    	List<Edge> edges = graph.getEdges();
            	    	List<Callable<Void>> todoEdges = new ArrayList<Callable<Void>>();
                        for (Edge edge : edges) {
                        	
                        	todoEdges.add(new Callable<Void>() {
                        		 public Void call() throws Exception {
                        			 Node sourceNode = edge.getSourceNode();
                                     Node targetNode = edge.getTargetNode();
                                     
                                     GraphElement sourceElem = elements.get(sourceNode.getConceptId());
                                     if (sourceElem == null) {
                                         IConcept concept = conceptCache.getConceptById(sourceNode.getConceptId());
                                         if (concept != null) {
                                             sourceElem = createElement(sourceNode, concept);
                                             elements.put(sourceNode.getConceptId(), sourceElem);
                                         }
                                     }
                                     GraphElement targetElem = elements.get(targetNode.getConceptId());
                                     if (targetElem == null) {
                                         IConcept concept = conceptCache.getConceptById(targetNode.getConceptId());
                                         if (concept != null) {
                                             targetElem = createElement(targetNode, concept);
                                             elements.put(targetNode.getConceptId(), targetElem);
                                         }
                                     }
                                     if (sourceElem != null && targetElem != null) {
                                         elements.put(edge.getId() + "", new GraphElement(new EdgeData(sourceElem.getData().getId(), targetElem.getData().getId(), edge.getId() + "", "")));
                                     }
                                     return null;
                        		 }
                        	});
                        }
                        try {
                			executorService.invokeAll(todoEdges);
                		} catch (InterruptedException e) {
                			// TODO Auto-generated catch block
                			e.printStackTrace();
                		}
                        return null;
            	    }
            	});
            }
        }
    	try {
			executorService.invokeAll(todoGraph);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<Collection<GraphElement>>(elements.values(), HttpStatus.OK);
    }

    private GraphElement createElement(Node node, IConcept concept) {
        GraphElement element = new GraphElement(new Data(concept.getId(), node.getLabel()));
        element.getData().setType(concept.getTypeId());
        if (concept.getTypeId().equals(personTypeId)) {
            element.getData().setColor(personNodeColor);
        } else if (concept.getTypeId().equals(institutionTypeId)) {
            element.getData().setColor(institutionNodeColor);
        } else if (concept.getTypeId().equals(locationTypeId)) {
            element.getData().setColor(locationNodeColor);
        } else {
            element.getData().setColor(generalNodeColor);
        }
        return element;
    }
}

package edu.asu.diging.wic.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.asu.diging.wic.core.service.IStatementService;
import edu.asu.diging.wic.web.cytoscape.Data;
import edu.asu.diging.wic.web.cytoscape.EdgeData;
import edu.asu.diging.wic.web.cytoscape.GraphElement;

@Controller
public class HomeController {
    
    @Autowired
    private IStatementService statementService;
    
    @Autowired
    private IGraphDBConnection graphDbConnection;
    
    @Autowired
    private IConceptpowerCache conceptCache;
    

    @RequestMapping(value = "/")
    public String home(Model model) {
        List<IConcept> concepts = statementService.getImportedConcepts();
        model.addAttribute("concepts", concepts);
        
        return "home";
    }
    
    @RequestMapping(value = "/network")
    public ResponseEntity<Collection<GraphElement>> getPersonNetwork() {
        List<String> uris = graphDbConnection.getAllPersons();
        Map<String, GraphElement> elements = new HashMap<>();
        for (String uri : uris) {
            List<Graph> graphs = graphDbConnection.getGraphs(uri);
            for (Graph graph : graphs) {
                for (Edge edge : graph.getEdges()) {
                    Node sourceNode = edge.getSourceNode();
                    Node targetNode = edge.getTargetNode();
                    
                    GraphElement sourceElem = elements.get(sourceNode.getConceptId());
                    if (sourceElem == null) {
                        IConcept concept = conceptCache.getConceptById(sourceNode.getConceptId());
                        sourceElem = new GraphElement(new Data(concept.getId(), sourceNode.getLabel()));
                        elements.put(sourceNode.getConceptId(), sourceElem);
                    }
                    GraphElement targetElem = elements.get(targetNode.getConceptId());
                    if (targetElem == null) {
                        IConcept concept = conceptCache.getConceptById(targetNode.getConceptId());
                        targetElem = new GraphElement(new Data(concept.getId(), targetNode.getLabel()));
                        elements.put(targetNode.getConceptId(), targetElem);
                    }
                    elements.put(edge.getId() + "", new GraphElement(new EdgeData(sourceElem.getData().getId(), targetElem.getData().getId(), edge.getId() + "", "")));
                }
            }
        }
        
        return new ResponseEntity<Collection<GraphElement>>(elements.values(), HttpStatus.OK);
    }
}

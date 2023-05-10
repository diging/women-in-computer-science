package edu.asu.diging.wic.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.model.impl.Edge;
import edu.asu.diging.wic.core.model.impl.Node;
import edu.asu.diging.wic.core.repository.EdgeRepository;
import edu.asu.diging.wic.core.service.IConceptTextService;
import edu.asu.diging.wic.web.cytoscape.Data;
import edu.asu.diging.wic.web.cytoscape.EdgeData;
import edu.asu.diging.wic.web.cytoscape.GraphElement;

@Controller
@PropertySource(value="classpath:/config.properties")
public class PersonController {
    
    @Autowired
    private EdgeRepository edgeRepository;
    
    @Autowired
    private IConceptpowerCache cache;
    
    @Autowired
    private IConceptTextService conceptTextService;
    
    @Value("${general.node.color}")
    private String generalNodeColor;
    
    @Value("${person.node.color}")
    private String personNodeColor;
    
    @Value("${location.node.color}")
    private String locationNodeColor;
    
    @Value("${institution.node.color}")
    private String institutionNodeColor;
    
    @Value("${concepts.type.institution}")
    private String institutionTypeId;

    @Value("${concepts.type.person}")
    private String personTypeId;
    
    @Value("${concepts.type.location}")
    private String locationTypeId;
    
    @RequestMapping(value = "/concept/{personId}", produces = MediaType.TEXT_HTML_VALUE)
    public String showPerson(@PathVariable("personId") String personId, Model model) throws IOException {
        
        IConcept concept = cache.getConceptById(personId);
        model.addAttribute("concept", concept);
        if (concept != null) {
            model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
            List<ConceptText> conceptTexts = conceptTextService.findByConceptId(personId);
            model.addAttribute("texts", conceptTexts);
        }
        
        return "person";
    }
    
    @RequestMapping("/concept/{conceptId}/statements")
    public String getConceptStatements(@PathVariable("conceptId") String conceptId, Model model) {
        
        IConcept concept = cache.getConceptById(conceptId);
        List<Edge> uniqueEdges = getEdges(concept.getAlternativeUris());
        
        model.addAttribute("edges", uniqueEdges);
        
        model.addAttribute("concept", concept);
        model.addAttribute("alternativeIdsString", String.join(",", concept.getAlternativeUris()));
        return "person/statements";
    }
    
    @RequestMapping(value = "/concept/{personId}/network")
    public ResponseEntity<Collection<GraphElement>> getPersonNetwork(@PathVariable("personId") String personId) {
        
        IConcept sourceConcept = cache.getConceptById(personId);
        Map<String, GraphElement> elements = new HashMap<>();
        
        List<Edge> uniqueEdges = getEdges(sourceConcept.getAlternativeUris());
        
        for (Edge edge : uniqueEdges) {
            Node sourceNode = edge.getSourceNode();
            Node targetNode = edge.getTargetNode();
            GraphElement sourceElem = elements.get(sourceNode.getConceptId());
            if (sourceElem == null) {
                IConcept concept = cache.getConceptById(sourceNode.getConceptId());
                sourceElem = createElement(sourceNode, concept);
                elements.put(sourceNode.getConceptId(), sourceElem);
            }
            GraphElement targetElem = elements.get(targetNode.getConceptId());
            if (targetElem == null) {
                IConcept concept = cache.getConceptById(targetNode.getConceptId());
                targetElem = createElement(targetNode, concept);
                elements.put(targetNode.getConceptId(), targetElem);
            }
            elements.put(edge.getId() + "", new GraphElement(new EdgeData(sourceElem.getData().getId(), targetElem.getData().getId(), edge.getId() + "", "")));
        }
        return new ResponseEntity<Collection<GraphElement>>(elements.values(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/concept/{personId}/secondary-network")
    public ResponseEntity<Collection<GraphElement>> getPersonSecondaryNetwork(@PathVariable("personId") String personId) {
        IConcept sourceConcept = cache.getConceptById(personId);
        List<String> sourceAlternativeUris = sourceConcept.getAlternativeUris();
        HashSet<String> sourceNodeUris = new HashSet<>(sourceAlternativeUris);
        Map<String, GraphElement> elements = new HashMap<>();
        
        List<Edge> primaryEdges = getEdges(sourceAlternativeUris);
        for (Edge primaryEdge : primaryEdges) {
            Node sourceNode;
            Node edgeNode;
            Node targetNode;
            if (sourceNodeUris.contains(primaryEdge.getSourceNode().getUri())) {
                sourceNode = primaryEdge.getSourceNode();
                edgeNode = primaryEdge.getTargetNode();
            } else {
                sourceNode = primaryEdge.getTargetNode();
                edgeNode = primaryEdge.getSourceNode();
            }
            GraphElement sourceElem = elements.get(sourceNode.getConceptId());
            if (sourceElem == null) {
                IConcept concept = cache.getConceptById(sourceNode.getConceptId());
                sourceElem = createElement(sourceNode, concept);
                elements.put(sourceNode.getConceptId(), sourceElem);
            }
            IConcept edgeConcept = cache.getConceptById(edgeNode.getUri());
            List<String> edgeAlternativeUris = edgeConcept.getAlternativeUris();
            HashSet<String> edgeNodeUris = new HashSet<>(edgeAlternativeUris);
            List<Edge> secondaryEdges = getEdges(edgeAlternativeUris);
            for (Edge secondaryEdge : secondaryEdges) {
                if (edgeNodeUris.contains(secondaryEdge.getSourceNode().getUri())) {
                    targetNode = secondaryEdge.getTargetNode();
                } else {
                    targetNode = secondaryEdge.getSourceNode();
                }
                if (targetNode.getType().equals(sourceNode.getType())) {
                    GraphElement targetElem = elements.get(targetNode.getConceptId());
                    if (targetElem == null) {
                        IConcept concept = cache.getConceptById(targetNode.getConceptId());
                        targetElem = createElement(targetNode, concept);
                        elements.put(targetNode.getConceptId(), targetElem);
                    }
                    elements.put(sourceElem.getData().getId() + "-" + targetElem.getData().getId(),
                            new GraphElement(new EdgeData(sourceElem.getData().getId(), targetElem.getData().getId(),
                                    sourceElem.getData().getId() + "-" + targetElem.getData().getId(),
                                    edgeNode.getLabel())));
                }
            }
        }
        return new ResponseEntity<Collection<GraphElement>>(elements.values(), HttpStatus.OK);
    }
    
    private List<Edge> getEdges(List<String> alternativeUris) {
        List<Edge> edgeList = edgeRepository.findBySourceNodeUriIn(alternativeUris);
        List<Edge> uniqueEdges = new ArrayList<Edge>();
        HashSet<String> uniqueEdgeStrings = new HashSet<>();
        
        for(int i = 0; i < edgeList.size(); i++) {
            String sourceAndTarget = edgeList.get(i).getSourceNode().getUri() + "-" + edgeList.get(i).getTargetNode().getUri();
            if(!uniqueEdgeStrings.contains(sourceAndTarget)) {
                uniqueEdgeStrings.add(sourceAndTarget);
                uniqueEdges.add(edgeList.get(i));
            } 
        }
        
        return uniqueEdges;
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

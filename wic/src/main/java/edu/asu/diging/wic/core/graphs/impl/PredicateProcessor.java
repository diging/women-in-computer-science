package edu.asu.diging.wic.core.graphs.impl;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import edu.asu.diging.wic.core.graphs.IPredicateProcessor;
import edu.asu.diging.wic.core.model.impl.Edge;
import edu.asu.diging.wic.core.util.ISourceUriPatternUtil;

@Service
@PropertySource("classpath:config.properties")
public class PredicateProcessor implements IPredicateProcessor {
    
    private final String represenationFolder = "representations/";
    private final String fileEnding = ".txt";
    
    @Autowired
    private ISourceUriPatternUtil patternUtil;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see edu.asu.diging.grazer.core.graphs.impl.IPredicateProcessor#setPredicateName(edu.asu.diging.grazer.core.model.impl.Edge, java.lang.String)
     */
    @Override
    public void setPredicateName(Edge edge, String transformationName) {
        Resource transResource = new ClassPathResource(represenationFolder + transformationName + fileEnding);
        Properties props = new Properties();
        try {
            props.load(transResource.getInputStream());
        } catch (IOException e) {
            logger.error("Can't load relationship file. Returning.", e);
            return;
        }
        
        String conceptId = "";
        if (edge.getConcept() != null) {
            conceptId = edge.getConcept().substring(edge.getConcept().lastIndexOf("/") + 1);
        }
        String edgeName = props.getProperty("relationship_name_" + conceptId);
        if (edgeName != null) {
            edge.setLabel(edgeName.replace("${link}", edge.getLabel()));
            return;
        }
        
        edgeName = props.getProperty("relationship_name");
        if (edgeName != null) {
            edge.setLabel(edgeName.replace("${link}", edge.getLabel()));
        }       
    }
    
    @Override
    public void setPredicateUri(Edge edge) {
        String transformed = patternUtil.getTransformedUri(edge.getSourceUri());
        if (transformed != null) {
            edge.setSourceUri(transformed);
        }
    }
}

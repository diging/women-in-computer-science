package edu.asu.diging.wic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.wiki.IWikiConnector;
import edu.asu.diging.wic.core.wiki.exceptions.WikipediaSummaryException;

@Controller
@PropertySource(value = "classpath:/config.properties")
public class GetConceptWikiSummaryController {

    @Autowired
    private IConceptpowerCache cache;

    @Autowired
    private IWikiConnector wikiConnector;

    @GetMapping("/concept/{conceptId}/wiki-summary")
    public ResponseEntity<String> getConceptSummary(@PathVariable("conceptId") String conceptId) {
        IConcept concept = cache.getConceptById(conceptId);
        if (concept == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(wikiConnector.getSummary(wikiConnector.getPageTitle(concept)), HttpStatus.OK);
        } catch (WikipediaSummaryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

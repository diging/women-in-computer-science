package edu.asu.diging.wic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.wiki.IWikiConnector;

@Controller
@PropertySource(value = "classpath:/config.properties")
public class GetConceptWikiSummaryController {

    @Value("${wikipedia.url.pattern}")
    private String wikiRegex;

    @Autowired
    private IConceptpowerCache cache;

    @Autowired
    private IWikiConnector wikiConnector;

    @GetMapping("/wiki-summary/concept/{conceptId}")
    public ResponseEntity<String> getConceptSummary(@PathVariable("conceptId") String conceptId) {
        IConcept concept = cache.getConceptById(conceptId);
        if (concept == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(wikiConnector.getSummary(wikiConnector.getPageTitle(concept)), HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }

    }
}

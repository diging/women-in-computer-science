package edu.asu.diging.wic.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.wiki.IWikiConnector;

@Controller
public class GetConceptWikiSummaryController {

    @Autowired
    private IConceptpowerCache cache;
    
    @Autowired
    private IWikiConnector wikiConnector;
    
    @GetMapping("/wiki-summary/concept/{conceptId}")
    public String getConceptSummary(@PathVariable("conceptId") String personId) {
        IConcept concept = cache.getConceptById(personId);
        Optional<String> wikiUri = concept.getAlternativeUris().stream().filter(uri -> uri.matches("https://en.wikipedia.org/wiki/*")).findFirst();
        String pageTitle;
        if (wikiUri.isPresent()) {
            String[] uriSplit = wikiUri.get().split("/");
            pageTitle = uriSplit[uriSplit.length-1];
        } else {
            pageTitle = String.join("_", concept.getWord().split(" "));
        }
        return wikiConnector.getSummary(pageTitle);
    }
}

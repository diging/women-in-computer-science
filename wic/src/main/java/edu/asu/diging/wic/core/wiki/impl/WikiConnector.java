package edu.asu.diging.wic.core.wiki.impl;

import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.wiki.IWikiConnector;

@Service
@PropertySource(value = "classpath:/config.properties")
public class WikiConnector implements IWikiConnector {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Value("${wikipedia.url.pattern}")
    private String wikiRegex;

    @Value("${wikipedia.summary.url}")
    private String wikiSummaryUri;

    private RestTemplate restTemplate;

    public WikiConnector() {
        restTemplate = new RestTemplate();
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.wiki.IWikiConnector#getSummary(java.lang.String)
     */
    @Override
    //Enable caching for this method
    public String getSummary(String pageTitle) {
        String summaryUri = String.format(wikiSummaryUri, pageTitle);
        try {
            WikiSummaryResponse response = restTemplate.getForObject(summaryUri, WikiSummaryResponse.class);
            return response.getExtract_html();
        } catch (HttpClientErrorException ex) {
            logger.error("No wikipedia summary found for page " + pageTitle, ex);
            return ex.getResponseBodyAsString();
        }
    }
    
    @Override
    public String getPageTitle(IConcept concept) {
        Optional<String> wikiUri = concept.getSimilarTo().stream().filter(uri -> Pattern.matches(wikiRegex, uri))
                .findFirst();
        String pageTitle;
        if (wikiUri.isPresent()) {
            String[] uriSplit = wikiUri.get().split("/");
            pageTitle = uriSplit[uriSplit.length - 1];
        } else {
            pageTitle = String.join("_", concept.getWord().split(" "));
        }
        return pageTitle;
    }

}

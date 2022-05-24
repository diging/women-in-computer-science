package edu.asu.diging.wic.core.wiki.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.wic.core.wiki.IWikiConnector;

@Service
public class WikiConnector implements IWikiConnector {

    private String wikiSummaryUri = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    private RestTemplate restTemplate;
    
    public WikiConnector() {
        restTemplate = new RestTemplate();
    }
    
    @Override
    public String getSummary(String pageTitle) {
        String pageSummaryUri = wikiSummaryUri + pageTitle + "?redirect=false";
        WikiSummaryResponse response = restTemplate.getForObject(pageSummaryUri, WikiSummaryResponse.class);
        return response.getExtract_html();
    }

}

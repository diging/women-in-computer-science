package edu.asu.diging.wic.core.wiki.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.wic.core.wiki.IWikiConnector;

@Service
@PropertySource(value = "classpath:/config.properties")
public class WikiConnector implements IWikiConnector {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${wikipedia.summary.url}")
    private String wikiSummaryUri;

    private RestTemplate restTemplate;

    public WikiConnector() {
        restTemplate = new RestTemplate();
    }

    @Override
    public String getSummary(String pageTitle) {
        wikiSummaryUri = String.format(wikiSummaryUri, pageTitle);
        try {
            WikiSummaryResponse response = restTemplate.getForObject(wikiSummaryUri, WikiSummaryResponse.class);
            return response.getExtract_html();
        } catch (HttpClientErrorException ex) {
            logger.error("No wikipedia summary found for page " + pageTitle, ex);
            throw ex;
        }
    }

}

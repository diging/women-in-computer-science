package edu.asu.diging.wic.core.wiki.impl;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.wiki.IWikiConnector;
import edu.asu.diging.wic.core.wiki.exceptions.WikipediaSummaryException;

@Service
@PropertySource(value = "classpath:/config.properties")
public class WikiConnector implements IWikiConnector {

	@Value("${wikipedia.url.pattern}")
	private String wikiRegex;

	@Value("${wikipedia.summary.url}")
	private String wikiSummaryUri;

	private RestTemplate restTemplate;

	public WikiConnector() {
		restTemplate = new RestTemplate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.diging.wic.core.wiki.IWikiConnector#getSummary(java.lang.String)
	 */
	@Override
	@Cacheable(value = "wiki_summaries", key = "#pageTitle")
	public String getSummary(String pageTitle) {
		String summaryUri = String.format(wikiSummaryUri, pageTitle);
		try {
			WikiSummaryResponse response = restTemplate.getForObject(summaryUri, WikiSummaryResponse.class);
			return response.getExtract_html();
		} catch (HttpClientErrorException ex) {
			throw new WikipediaSummaryException("Error fetching summary for the page" + pageTitle, ex);
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
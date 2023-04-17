package edu.asu.diging.wic.core.wiki;

import edu.asu.diging.wic.core.model.IConcept;

public interface IWikiConnector {
    
    /**
     * Fetches wikipedia summary for the page with the given title
     * @param pageTitle title for which the summary is to be retrieved
     * @return The page summary in html format
     */
    String getSummary(String pageTitle);

    String getPageTitle(IConcept concept);

}

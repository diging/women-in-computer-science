package edu.asu.diging.wic.core.wiki.impl;

/**
 * Holds the wikipedia summary details for a page title
 * @author Maulik Limbadiya
 *
 */
public class WikiSummaryResponse {
    
    private String description;
    private String extract;
    private String extract_html;
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getExtract() {
        return extract;
    }
    public void setExtract(String extract) {
        this.extract = extract;
    }
    public String getExtract_html() {
        return extract_html;
    }
    public void setExtract_html(String extract_html) {
        this.extract_html = extract_html;
    }
    
}

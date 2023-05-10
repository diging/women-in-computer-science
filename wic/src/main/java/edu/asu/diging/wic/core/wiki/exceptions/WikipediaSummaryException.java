package edu.asu.diging.wic.core.wiki.exceptions;

public class WikipediaSummaryException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public WikipediaSummaryException(String message) {
        super(message);
    }

    public WikipediaSummaryException(String message, Throwable cause) {
        super(message, cause);
    }

}

package edu.asu.diging.wic.core.service;

import java.util.List;

import edu.asu.diging.wic.core.model.impl.ConceptText;

public interface IConceptTextService {

    /**
     * <p>Method is used to add conceptText model/object in database
     * </p>
     * @param conceptText Model of conceptText which will be stored 
     */
    void addText(ConceptText conceptText);

    /**
     * <p>Method is used to find all conceptText to be shown on a particular page
     * </p>
     * @param Pagenumber the pageNumber which is to be displayed
     * @return conceptText List of conceptText belonging to that pageNumber
     */
    List<ConceptText> findAll(String pageNumber);

    /**
     * <p>Method used to delete a particular conceptText
     * </p>
     * @param id id of the conceptText model which is to be deleted
     */
    void deleteText(String id);

    /**
     * <p>Method used to update a particular conceptText
     * </p>
     * @param updatedForm conceptText Model with updated information
     * @param modifiedBy the user who modified the conceptText
     */
    void updateText(ConceptText updatedForm, String modifiedBy);

    /**
     * <p>Method used to update a particular single conceptText by id 
     * </p>
     * @param id id of the conceptText whose details you want to fetch
     * @return conceptText model of the conceptText fetched from id
     */
    ConceptText getTextById(String id);

    /**
     * <p>Method used to get total count from database
     * </p>
     * @return count of the number of conceptText in database
     */
    int getTextCount();
}

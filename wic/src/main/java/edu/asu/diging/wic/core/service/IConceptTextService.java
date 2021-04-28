package edu.asu.diging.wic.core.service;

import java.util.List;

import edu.asu.diging.wic.core.model.impl.ConceptText;

public interface IConceptTextService {

    /**
     * <p>Method is used to add conceptText model/object in database
     * </p>
     * @param conceptText Model of conceptText which will be stored 
     * @param name String of username who is adding text 
     */
    ConceptText addText(ConceptText conceptText, String name);

    /**
     * <p>Method is used to find all conceptText from database.
     * </p>
     * @param Pagenumber the pageNumber which is to be displayed
     * @param itemsPerPage Integer of number of items to display on a single page
     * @return conceptText List of conceptText belonging to that pageNumber
     */
    List<ConceptText> findAll(Integer page, Integer itemsPerPage);

    /**
     * <p>Method used to delete a particular conceptText
     * </p>
     * @param id id of the conceptText model which is to be deleted
     */
    void deleteText(Long id);

    /**
     * <p>Method used to update a particular conceptText by retrieving based on id
     * </p>
     * @param updatedForm conceptText Model with updated information
     * @param modifiedBy the user who modified the conceptText
     * @param id of the conceptText which will be updated
     */
    ConceptText updateText(ConceptText updatedForm, String modifiedBy, Long id);

    /**
     * <p>Method used to update a particular single conceptText by id 
     * </p>
     * @param id id of the conceptText whose details you want to fetch
     * @return conceptText model of the conceptText fetched from id
     */
    ConceptText getTextById(Long id);

    /**
     * <p>Method used to get total count of conceptText from database
     * </p>
     * @return count of the number of conceptText in database
     */
    long getTextCount();
}

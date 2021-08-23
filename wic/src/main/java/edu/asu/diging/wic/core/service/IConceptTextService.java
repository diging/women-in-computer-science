package edu.asu.diging.wic.core.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import edu.asu.diging.wic.core.model.impl.ConceptText;

public interface IConceptTextService {

    /**
     * Method is used to add conceptText model/object in database
     * 
     * @param conceptText Model of conceptText which will be stored
     * @param name        String of username who is adding text
     */
    ConceptText addText(ConceptText conceptText, String name);

    /**
     * Fetches the page of concept texts
     * 
     * @param page         Request page number
     * @param itemsPerPage Max items per page
     * @param sortBy       Attribute to sort by
     * @param order        Sorting order i.e., ASC or DESC
     * @return List of concept texts on the given page
     */
    List<ConceptText> findAll(Integer page, Integer itemsPerPage, String sortBy, Direction order);

    /**
     * Method used to delete a particular conceptText
     * 
     * @param id id of the conceptText model which is to be deleted
     */
    void deleteText(Long id);

    /**
     * Method used to update a particular conceptText by retrieving based on id
     * 
     * @param updatedForm conceptText Model with updated information
     * @param modifiedBy  the user who modified the conceptText
     * @param id          of the conceptText which will be updated
     */
    ConceptText updateText(ConceptText conceptText, String modifiedBy, Long id);

    /**
     * Method used to update a particular single conceptText by id
     * 
     * @param id id of the conceptText whose details you want to fetch
     * @return conceptText model of the conceptText fetched from id
     */
    ConceptText getTextById(Long id);

    /**
     * Method used to get total count of conceptText from database
     * 
     * @return count of the number of conceptText in database
     */
    long getTextCount();
}

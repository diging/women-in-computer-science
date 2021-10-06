package edu.asu.diging.wic.core.service.impl;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.exceptions.CannotFindConceptTextException;
import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.repository.ConceptTextRepository;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Service
@Transactional
public class ConceptTextService implements IConceptTextService {

    @Autowired
    private ConceptTextRepository conceptRepository;

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.IConceptTextService#addText(edu.asu.diging.wic.core.model.impl.ConceptText, java.lang.String)
     */
    @Override
    public ConceptText addText(ConceptText conceptText, String name) {
        conceptText.setAddedOn(OffsetDateTime.now());
        conceptText.setAddedBy(name);
        return conceptRepository.save(conceptText);
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.IConceptTextService#findAll(java.lang.Integer, java.lang.Integer, java.lang.String, org.springframework.data.domain.Sort.Direction)
     */
    @Override
    public Page<ConceptText> findAll(Integer page, Integer itemsPerPage, String sortBy, Direction order) {
        Pageable pagination = PageRequest.of(page - 1, itemsPerPage, Sort.by(order, sortBy));
        return conceptRepository.findAll(pagination);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.IConceptTextService#findByConceptId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Page<ConceptText> findByConceptId(Integer page, Integer itemsPerPage, String conceptId) {
        return conceptRepository.findByConceptId(conceptId, PageRequest.of(page - 1, itemsPerPage));
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.IConceptTextService#deleteText(java.lang.Long)
     */
    @Override
    public void deleteText(Long id) {
        if (id != null) {
            conceptRepository.deleteById(id);
        }
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.IConceptTextService#updateText(edu.asu.diging.wic.core.model.impl.ConceptText, java.lang.String, java.lang.Long)
     */
    @Override
    public ConceptText updateText(ConceptText conceptText, String modifiedBy, Long id) throws CannotFindConceptTextException {
        Optional<ConceptText> optionalText = conceptRepository.findById(id);
        if (!optionalText.isPresent()) {
            throw new CannotFindConceptTextException("Concept text with id " + id + " does not exist");
        }
        conceptText.setId(id);
        conceptText.setModifiedby(modifiedBy);
        conceptText.setModifiedOn(OffsetDateTime.now());
        return conceptRepository.save(conceptText);
    }

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.IConceptTextService#getTextById(java.lang.Long)
     */
    @Override
    public ConceptText getTextById(Long id) {
        Optional<ConceptText> conceptTextOptional = conceptRepository.findById(id);
        if (conceptTextOptional.isPresent()) {
            return conceptTextOptional.get();
        }
        return null;
    }

}

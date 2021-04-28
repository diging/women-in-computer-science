package edu.asu.diging.wic.core.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.repository.ConceptTextDatabaseRepository;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Service
@Transactional
public class ConceptTextService implements IConceptTextService {

    @Autowired
    private ConceptTextDatabaseRepository conceptTextDatabaseConnection;

    @Override
    public ConceptText addText(ConceptText conceptText, String name) {

        conceptText.setAddedOn(OffsetDateTime.now());
        conceptText.setAddedBy(name);
        return conceptTextDatabaseConnection.save(conceptText);
    }

    @Override
    public List<ConceptText> findAll(Integer page, Integer itemsPerPage) {

        Pageable pagination = PageRequest.of(page-1, itemsPerPage);
        return conceptTextDatabaseConnection.findAll(pagination).getContent();
    }

    @Override
    public void deleteText(Long id) {
        if(id != null) {
            conceptTextDatabaseConnection.deleteById(id);
        }
    }

    @Override
    public ConceptText updateText(ConceptText updatedForm, String modifiedBy, Long id) {

        Optional<ConceptText> data = conceptTextDatabaseConnection.findById(id);
        ConceptText conceptTextUpdate = data.get();
        conceptTextUpdate.setText(updatedForm.getText());
        conceptTextUpdate.setTitle(updatedForm.getTitle());
        conceptTextUpdate.setAuthor(updatedForm.getAuthor());
        conceptTextUpdate.setModifiedby(modifiedBy);
        conceptTextUpdate.setModifiedOn(OffsetDateTime.now());
        conceptTextDatabaseConnection.save(conceptTextUpdate);
        
        return conceptTextUpdate;
    }

    @Override
    public ConceptText getTextById(Long id) {
        
        return conceptTextDatabaseConnection.findById(id).get();
    }

    @Override
    public long getTextCount() {

        return conceptTextDatabaseConnection.count();
    }

}

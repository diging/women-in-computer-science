package edu.asu.diging.wic.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public void addText(ConceptText conceptText, String name) {

        conceptText.setAddedOn(new Date().getTime()+"");
        conceptText.setAddedBy(name);
        conceptTextDatabaseConnection.save(conceptText);
    }

    @Override
    public List<ConceptText> findAll(String page, Integer itemsPerPage) {

        Pageable pagination = PageRequest.of(Integer.parseInt(page)-1, itemsPerPage);
        Page<ConceptText> dataFromDb = conceptTextDatabaseConnection.findAll(pagination);

        List<ConceptText> data = new ArrayList<ConceptText>();
        if(dataFromDb != null) {
            dataFromDb.getContent().forEach(i -> data.add(i));
        }
        return data;
    }

    @Override
    public void deleteText(String id) {

        conceptTextDatabaseConnection.deleteById(Long.parseLong(id));
    }

    @Override
    public void updateText(ConceptText updatedForm, String modifiedBy) {

        Optional<ConceptText> data = conceptTextDatabaseConnection.findById(updatedForm.getId());
        ConceptText conceptTextUpdate = data.get();
        conceptTextUpdate.setText(updatedForm.getText());
        conceptTextUpdate.setTitle(updatedForm.getTitle());
        conceptTextUpdate.setAuthor(updatedForm.getAuthor());
        conceptTextUpdate.setModifiedby(modifiedBy);
        conceptTextUpdate.setModifiedOn(new Date().getTime()+"");
        conceptTextDatabaseConnection.save(conceptTextUpdate);
    }

    @Override
    public ConceptText getTextById(String id) {
        return conceptTextDatabaseConnection.findById(Long.parseLong(id)).get();
    }

    @Override
    public long getTextCount() {

        return conceptTextDatabaseConnection.count();
    }

}

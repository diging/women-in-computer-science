package edu.asu.diging.wic.core.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.repository.ConceptTextDatabaseRepository;
import edu.asu.diging.wic.core.service.impl.ConceptTextService;

public class ConceptTextServiceTest {

    @Mock
    private ConceptTextDatabaseRepository conceptTextDatabaseConnection;
    
    @InjectMocks
    private ConceptTextService conceptTextService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void testAddText() {
        
        ConceptText conceptText = new ConceptText();
        conceptText.setAuthor("Smit");
        conceptText.setConceptId("WC123");
        conceptText.setText("The great Scientist");
        conceptText.setTitle("Information");

        when(conceptTextDatabaseConnection.save(conceptText)).thenReturn(conceptText);
        ConceptText savedText = conceptTextService.addText(conceptText, "admin");
        
        Assert.assertEquals(conceptText, savedText);
        Assert.assertEquals(conceptText.getAddedBy(), "admin");
        Assert.assertNotNull(conceptText.getAddedOn());
        
        verify(conceptTextDatabaseConnection).save(conceptText);
    }

    @Test
    public void testFindAll() {
        
        List<ConceptText> dataObj = new ArrayList<>();
        dataObj.add(new ConceptText());
        dataObj.add(new ConceptText());
        
        Page<ConceptText> dataFromDb = new PageImpl<ConceptText>(dataObj);
        when(conceptTextDatabaseConnection.findAll(any(Pageable.class)))
            .thenReturn(dataFromDb);
        
        List<ConceptText> objFromDb = conceptTextService.findAll(1, 2);
        Assert.assertEquals(objFromDb.size(), 2);
        verify(conceptTextDatabaseConnection).findAll(any(Pageable.class));
    }

    @Test
    public void testDeleteText_forSuccess() {
        conceptTextService.deleteText(1L);
        verify(conceptTextDatabaseConnection, times(1)).deleteById(eq(1L));
    }
    
    @Test
    public void testDeleteText_whenIdIsNull() {
        conceptTextService.deleteText(null);
        verify(conceptTextDatabaseConnection, never()).deleteById(null);
    }

    @Test
    public void testUpdateText() {
        ConceptText updatedForm = new ConceptText();
        updatedForm.setText("Updated text");
        updatedForm.setTitle("Updated title");
        updatedForm.setAuthor("Updated author");
        
        ConceptText oldForm = new ConceptText();
        oldForm.setText("Old text");
        oldForm.setTitle("Old title");
        oldForm.setAuthor("Old Author");
        oldForm.setModifiedby("Admin");
        
        OffsetDateTime dateOnWhichModified = OffsetDateTime.now();
        oldForm.setModifiedOn(dateOnWhichModified);
        
        Optional<ConceptText> oldObjFromDb = Optional.of(oldForm);
        when(conceptTextDatabaseConnection.findById(1L)).thenReturn(oldObjFromDb);
        
        ConceptText updatedSavedObj = conceptTextService.updateText(updatedForm, "Smit", 1L);
        
        Assert.assertEquals(updatedSavedObj.getText(), "Updated text");
        Assert.assertEquals(updatedSavedObj.getTitle(), "Updated title");
        Assert.assertEquals(updatedSavedObj.getAuthor(), "Updated author");
        Assert.assertEquals(updatedSavedObj.getModifiedby(), "Smit");
        
        verify(conceptTextDatabaseConnection, times(1)).findById(1L);
        verify(conceptTextDatabaseConnection, times(1)).save(oldForm);
    }

    @Test
    public void testGetTextById() {
        ConceptText dataInDb = new ConceptText();
        dataInDb.setId(1L);
        Optional<ConceptText> data = Optional.of(dataInDb);
        
        when(conceptTextDatabaseConnection.findById(1L)).thenReturn(data);
        
        ConceptText dataFromGetById = conceptTextService.getTextById(1L);
        
        Assert.assertEquals(dataInDb, dataFromGetById);
        verify(conceptTextDatabaseConnection, times(1)).findById(1L);
    }

    @Test
    public void testGetTextCount() {
        
        when(conceptTextDatabaseConnection.count()).thenReturn(10L);
        
        long totalCountInDb = conceptTextService.getTextCount();
        
        Assert.assertEquals(10L, totalCountInDb);
        verify(conceptTextDatabaseConnection,times(1)).count();
    }

}

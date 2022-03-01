package edu.asu.diging.wic.core.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.data.domain.Sort.Direction;

import edu.asu.diging.wic.core.exceptions.CannotFindConceptTextException;
import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.repository.ConceptTextRepository;

public class ConceptTextServiceTest {

    @Mock
    private ConceptTextRepository conceptRepository;

    @InjectMocks
    private ConceptTextService conceptTextService;

    private ConceptText conceptText1;
    private ConceptText conceptText2;
    private ConceptText conceptText3;
    private Long id;

    private static final String SORTBY_ATTR = "title";
    private static final String CONCEPT_ID = "WC123";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        id = new Long(1L);

        conceptText1 = new ConceptText();
        conceptText1.setId(id);
        conceptText1.setAuthor("Smit");
        conceptText1.setConceptId(CONCEPT_ID);
        conceptText1.setText("The great Scientist");
        conceptText1.setTitle("A Title");

        conceptText2 = new ConceptText();
        conceptText2.setAuthor("Smit");
        conceptText2.setConceptId("WC124");
        conceptText2.setText("The great Researcher");
        conceptText2.setTitle("B Title");
        
        conceptText3 = new ConceptText();
        conceptText3.setAuthor("Smit");
        conceptText3.setConceptId(CONCEPT_ID);
        conceptText3.setText("The great Student");
        conceptText3.setTitle("C Title");
    }

    @Test
    public void test_addText() {
        when(conceptRepository.save(conceptText1)).thenReturn(conceptText1);
        ConceptText savedText = conceptTextService.addText(conceptText1, "admin");

        Assert.assertEquals(conceptText1, savedText);
        Assert.assertEquals(conceptText1.getAddedBy(), "admin");
        Assert.assertNotNull(conceptText1.getAddedOn());
    }

    @Test
    public void test_findAll() {

        List<ConceptText> dataObj = new ArrayList<>();
        dataObj.add(conceptText1);
        dataObj.add(conceptText2);

        Page<ConceptText> dataFromDb = new PageImpl<ConceptText>(dataObj);
        when(conceptRepository.findAll(any(Pageable.class))).thenReturn(dataFromDb);

        List<ConceptText> responseList = conceptTextService.findAll(1, 2, SORTBY_ATTR, Direction.ASC).getContent();
        Assert.assertEquals(2, responseList.size());
        Assert.assertEquals(conceptText1, responseList.get(0));
        Assert.assertEquals(conceptText2, responseList.get(1));
    }
    
    @Test
    public void test_findByConceptId_success() {
        List<ConceptText> conceptTexts = new ArrayList<>();
        conceptTexts.add(conceptText1);
        conceptTexts.add(conceptText3);

        when(conceptRepository.findByConceptId(CONCEPT_ID)).thenReturn(conceptTexts);

        List<ConceptText> actualTexts = conceptTextService.findByConceptId(CONCEPT_ID);
        Assert.assertEquals(conceptTexts.size(), actualTexts.size());
        for (ConceptText text : conceptTexts) {
            Assert.assertTrue(actualTexts.contains(text));
        }
    }
    
    @Test
    public void test_findByConceptId_empty() {
        when(conceptRepository.findByConceptId(CONCEPT_ID)).thenReturn(new ArrayList<>());

        List<ConceptText> actualTexts = conceptTextService.findByConceptId("1234");
        Assert.assertEquals(0, actualTexts.size());
    }

    @Test
    public void test_deleteText_forSuccess() {
        conceptTextService.deleteText(1L);
        verify(conceptRepository, times(1)).deleteById(eq(1L));
    }

    @Test
    public void test_updateText_textExists() throws CannotFindConceptTextException {
        ConceptText updatedText = new ConceptText();
        updatedText.setText("Updated text");
        updatedText.setTitle("Updated title");
        updatedText.setAuthor("Updated author");

        ConceptText oldText = new ConceptText();
        when(conceptRepository.findById(id)).thenReturn(Optional.of(oldText));
        when(conceptRepository.save(updatedText)).thenReturn(updatedText);
        ConceptText updatedSavedObj = conceptTextService.updateText(updatedText, "Smit", id);

        Assert.assertEquals(updatedText.getText(), updatedSavedObj.getText());
        Assert.assertEquals(updatedText.getTitle(), updatedSavedObj.getTitle());
        Assert.assertEquals(updatedText.getAuthor(), updatedSavedObj.getAuthor());
        Assert.assertEquals(updatedText.getModifiedby(), updatedSavedObj.getModifiedby());
    }

    @Test(expected = CannotFindConceptTextException.class)
    public void test_updateText_textDoesNotExist() throws CannotFindConceptTextException {
        Long id2 = new Long(2L);
        ConceptText updatedText = new ConceptText();

        when(conceptRepository.findById(id2)).thenReturn(Optional.empty());
        conceptTextService.updateText(updatedText, "Smit", id2);
    }

    @Test
    public void test_getTextById_exists() {
        when(conceptRepository.findById(id)).thenReturn(Optional.of(conceptText1));

        ConceptText response = conceptTextService.getTextById(id);
        Assert.assertEquals(conceptText1, response);
    }

    @Test
    public void test_getTextById_doesNotExists() {
        when(conceptRepository.findById(2L)).thenReturn(Optional.empty());
        ConceptText emptyResponse = conceptTextService.getTextById(2L);
        Assert.assertNull(emptyResponse);
    }

}

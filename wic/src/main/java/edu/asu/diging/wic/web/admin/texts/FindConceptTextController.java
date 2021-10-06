package edu.asu.diging.wic.web.admin.texts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class FindConceptTextController {
    
    @Autowired
    IConceptTextService conceptTextService;
    
    @RequestMapping(value = "/admin/text/find/concept/{conceptId}", method = RequestMethod.GET)
    public ResponseEntity<List<ConceptText>> findAll(@PathVariable("conceptId") String conceptId, 
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, 
            @RequestParam(value = "itemsPerPage", defaultValue = "10", required = false) Integer itemsPerPage) {
        List<ConceptText> conceptTexts = conceptTextService.findByConceptId(page, itemsPerPage, conceptId).getContent();
        return new ResponseEntity<List<ConceptText>>(conceptTexts, HttpStatus.OK);
    }

}

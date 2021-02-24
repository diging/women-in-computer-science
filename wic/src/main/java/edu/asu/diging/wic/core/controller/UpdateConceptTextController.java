package edu.asu.diging.wic.core.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class UpdateConceptTextController {
	
	@Autowired
	private IConceptTextService conceptTextService;

    @RequestMapping(value="/admin/text/{id}/edit", method=RequestMethod.GET)
    public String editConceptTextView( @PathVariable("id") String id, Model model,Principal principal) {
        
        ConceptText dataObj = conceptTextService.getTextById(id);
        model.addAttribute("idOfData", dataObj.getId());
        model.addAttribute("title", dataObj.getTitle());
        model.addAttribute("text", dataObj.getText());
        model.addAttribute("conceptId", dataObj.getConceptId());
        model.addAttribute("author", dataObj.getAuthor());
        return "admin/text/edit";
    }

	@RequestMapping(value="/admin/text/update", method=RequestMethod.POST)
	public ResponseEntity<String> updateTextData(@Valid ConceptText updatedForm,
	        BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttrs) {
		
	    if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        conceptTextService.updateText(updatedForm, principal.getName());
	    return new ResponseEntity<>(HttpStatus.OK);
	}
}

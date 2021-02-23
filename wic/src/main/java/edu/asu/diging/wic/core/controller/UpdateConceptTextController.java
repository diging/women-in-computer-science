package edu.asu.diging.wic.core.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class UpdateConceptTextController {
	
	@Autowired
	private IConceptTextService conceptTextService;

	@RequestMapping(value="/admin/text/update", method=RequestMethod.POST)
	public ResponseEntity<String> updateTextData(ConceptText updatedForm,
			Principal principal, RedirectAttributes redirectAttrs) {
		
		if(updatedForm.getAuthor() == null || updatedForm.getAuthor().trim().isEmpty()) {
			return new ResponseEntity<>("Author missing",HttpStatus.NO_CONTENT);
		}
		if(updatedForm.getTitle() == null || updatedForm.getTitle().trim().isEmpty()) {
			return new ResponseEntity<>("Title missing",HttpStatus.NO_CONTENT);
		}
		conceptTextService.updateText(updatedForm, principal.getName());
	    return new ResponseEntity<>(HttpStatus.OK);
	}
}

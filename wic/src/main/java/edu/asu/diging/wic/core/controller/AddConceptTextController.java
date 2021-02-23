package edu.asu.diging.wic.core.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class AddConceptTextController {
	
	@Autowired
	private IConceptTextService conceptTextService;
	
	@RequestMapping(value="/admin/text/addTextView", method=RequestMethod.GET)
	public String addText(Model model) {
	    return "admin/text/addTextView";
	}
		
	@RequestMapping(value="/admin/text/add", method=RequestMethod.POST)
	public String addTextData(ConceptText formData, 
			Principal principal, RedirectAttributes redirectAttrs) {
	
		if(formData.getConceptId() == null || formData.getConceptId().trim().isEmpty()) {
			return new ResponseEntity<String>("ConceptId missing",HttpStatus.NO_CONTENT).getBody();
		}
		if(formData.getAuthor() == null || formData.getAuthor().trim().isEmpty()) {
			return new ResponseEntity<String>("Author missing",HttpStatus.NO_CONTENT).getBody();
		}
		if(formData.getTitle() == null || formData.getTitle().trim().isEmpty()) {
			return new ResponseEntity<String>("Title missing",HttpStatus.NO_CONTENT).getBody();
		}
		formData.setAddedOn(new Date().getTime()+"");
		formData.setAddedBy(principal.getName());
		conceptTextService.addText(formData);
	    return  "redirect:/admin/text/list?pageNumber=1";
	}
}

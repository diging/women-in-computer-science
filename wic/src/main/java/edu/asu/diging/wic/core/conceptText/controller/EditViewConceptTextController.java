package edu.asu.diging.wic.core.conceptText.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class EditViewConceptTextController {
	
	@Autowired
	private IConceptTextService conceptTextService;

	@RequestMapping(value="/admin/text/{id}/edit", method=RequestMethod.GET)
	public String editConceptTextView( @PathVariable("id") String id, Model model,Principal principal) {
		
		ConceptText dataObj = conceptTextService.getConceptTextById(id);
		model.addAttribute("idOfData", dataObj.getId());
		model.addAttribute("title", dataObj.getTitle());
		model.addAttribute("text", dataObj.getText());
		model.addAttribute("conceptId", dataObj.getConceptId());
		model.addAttribute("author", dataObj.getAuthor());
	    return "admin/text/edit";
	}
}

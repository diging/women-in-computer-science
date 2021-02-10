package edu.asu.diging.wic.core.conceptText.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;
import edu.asu.diging.wic.core.model.impl.ConceptText;

@Controller
public class DeleteConceptTextController {

	@Autowired
	private IConceptTextService conceptTextService;
	
	
	@RequestMapping(value="/admin/text/delete/{id}", method=RequestMethod.DELETE)
	public String deleteText(@PathVariable("id") String id, Model model) {
		
		conceptTextService.deleteText(id);
		List<ConceptText> allConceptText = conceptTextService.findAll();
		model.addAttribute("allConceptText",allConceptText);
		return "admin/text/list";
	}
}

package edu.asu.diging.wic.core.conceptText.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;
import edu.asu.diging.wic.core.model.impl.ConceptText;

@Controller
public class ShowConceptTextController {
	
	@Autowired
	private IConceptTextService conceptTextService;
	
	@RequestMapping(value="/admin/text/list", method=RequestMethod.GET)
	public String findAll(Model model) {
		
		List<ConceptText> allConceptText = conceptTextService.findAll();
		model.addAttribute("allConceptText",allConceptText);
		return "admin/text/list";
	}

}

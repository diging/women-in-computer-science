package edu.asu.diging.wic.core.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class ShowConceptTextController {
	
	@Autowired
	private IConceptTextService conceptTextService;
	
	@RequestMapping(value="/admin/text/list", method=RequestMethod.GET)
	public String findAll(@RequestParam(value = "pageNumber", required = true)String pageNumber, Model model, Principal principal) {
		
		List<ConceptText> allConceptText = conceptTextService.findAll(pageNumber);
		model.addAttribute("allConceptText",allConceptText);
		int pagesCount = conceptTextService.getCount() % 5 == 0 ? 
		        (conceptTextService.getCount()/5) : (conceptTextService.getCount()/5) + 1;
		model.addAttribute("totalPages", pagesCount);
		model.addAttribute("currentPageNumber", Integer.parseInt(pageNumber));
		return "admin/text/list";
	}

}

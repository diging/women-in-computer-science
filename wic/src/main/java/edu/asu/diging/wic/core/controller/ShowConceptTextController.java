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
    public String findAll(@RequestParam(value = "page", required = true)String page,
            Model model, Principal principal) {

        List<ConceptText> allConceptText = conceptTextService.findAll(page);
        model.addAttribute("allConceptText",allConceptText);
        long totalEntriesInDb = conceptTextService.getTextCount();
        long pagesCount = totalEntriesInDb % 5 == 0 ? 
                (totalEntriesInDb/5) : (totalEntriesInDb/5) + 1;
        model.addAttribute("totalPages", pagesCount);
        model.addAttribute("currentPageNumber", Long.parseLong(page));
        return "admin/text/list";
    }

}

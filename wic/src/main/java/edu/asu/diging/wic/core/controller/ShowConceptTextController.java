package edu.asu.diging.wic.core.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
@PropertySource("classpath:config.properties")
public class ShowConceptTextController {

    @Autowired
    private IConceptTextService conceptTextService;
    
    @Value("${itemsPerPage}")
    private Integer itemsPerPage;

    @RequestMapping(value="/admin/text/list", method=RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false) String page,
            Model model, Principal principal) {

        if(page == null || page.trim().equals("")) {
            page = "1";
        }
        List<ConceptText> allConceptText = conceptTextService.findAll(page, itemsPerPage);
        model.addAttribute("allConceptText",allConceptText);
        long totalEntriesInDb = conceptTextService.getTextCount();
        long pagesCount = totalEntriesInDb % itemsPerPage == 0 ? 
                (totalEntriesInDb/itemsPerPage) : (totalEntriesInDb/itemsPerPage) + 1;
        model.addAttribute("totalPages", pagesCount);
        model.addAttribute("currentPageNumber", Long.parseLong(page));
        return "admin/text/list";
    }

}

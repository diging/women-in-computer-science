package edu.asu.diging.wic.core.controller;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String addTextData(@Valid ConceptText formData, BindingResult bindingResult, 
            Principal principal, RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors()) {
            return "admin/text/addTextView";
        }

        formData.setAddedOn(new Date().getTime()+"");
        formData.setAddedBy(principal.getName());
        conceptTextService.addText(formData);
        return  "redirect:/admin/text/list?pageNumber=1";
    }
}

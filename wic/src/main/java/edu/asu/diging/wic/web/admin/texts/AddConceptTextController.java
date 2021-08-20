package edu.asu.diging.wic.web.admin.texts;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class AddConceptTextController {

    @Autowired
    private IConceptTextService conceptTextService;

    @RequestMapping(value="/admin/text/add", method=RequestMethod.GET)
    public String addText(Model model) {
        model.addAttribute("conceptTextFormData", new ConceptText());
        return "admin/text/add";
    }

    @RequestMapping(value="/admin/text/add", method=RequestMethod.POST)
    public String addTextData(Model model,@ModelAttribute("conceptTextFormData") 
        @Valid ConceptText formData, BindingResult bindingResult, Principal principal, 
        RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("conceptTextFormData", formData);
            return "admin/text/add";
        }

        conceptTextService.addText(formData, principal.getName());
        return "redirect:/admin/text/list";
    }
}

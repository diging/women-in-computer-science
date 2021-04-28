package edu.asu.diging.wic.web.conceptText;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class UpdateConceptTextController {

    @Autowired
    private IConceptTextService conceptTextService;

    @RequestMapping(value="/admin/text/{id}/edit", method=RequestMethod.GET)
    public String editConceptTextView( @PathVariable("id") Long id,
            Model model,Principal principal) {

        ConceptText conceptText = conceptTextService.getTextById(id);
        model.addAttribute("conceptTextFormData", conceptText);
        return "admin/text/edit";
    }

    @RequestMapping(value="/admin/text/{id}/edit", method=RequestMethod.POST)
    public String updateTextData( @PathVariable("id") Long id, Model model,
            @ModelAttribute("conceptTextFormData") @Valid ConceptText updatedForm, 
            BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("conceptTextFormData", updatedForm);
            return "admin/text/edit";
        }
        conceptTextService.updateText(updatedForm, principal.getName(), id);
        return "redirect:/admin/text/list?page=1";
    }
}
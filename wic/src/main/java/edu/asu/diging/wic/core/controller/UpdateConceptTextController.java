package edu.asu.diging.wic.core.controller;

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
    public String editConceptTextView( @PathVariable("id") String id,
            Model model,Principal principal) {

        ConceptText conceptText = conceptTextService.getTextById(id);
        model.addAttribute("idOfData", conceptText.getId());
        model.addAttribute("title", conceptText.getTitle());
        model.addAttribute("text", conceptText.getText());
        model.addAttribute("conceptId", conceptText.getConceptId());
        model.addAttribute("author", conceptText.getAuthor());
        model.addAttribute("conceptTextFormData", conceptText);
        return "admin/text/edit";
    }

    @RequestMapping(value="/admin/text/update", method=RequestMethod.POST)
    public String updateTextData(Model model,@ModelAttribute("conceptTextFormData") @Valid ConceptText updatedForm,
            BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("conceptTextFormData", updatedForm);
            return "admin/text/edit";
//            return "redirect:/admin/text/"+updatedForm.getId()+"/edit";
        }
        conceptTextService.updateText(updatedForm, principal.getName());
        return "redirect:/admin/text/list?page=1";
    }
}

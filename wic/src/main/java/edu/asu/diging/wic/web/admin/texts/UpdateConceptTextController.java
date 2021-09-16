package edu.asu.diging.wic.web.admin.texts;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.exceptions.CannotFindConceptTextException;
import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class UpdateConceptTextController {

    @Autowired
    private IConceptTextService conceptTextService;

    @GetMapping(value = "/admin/text/{id}/edit")
    public String editConceptTextView(@PathVariable("id") Long id, Model model, Principal principal) {
        ConceptText conceptText = conceptTextService.getTextById(id);
        if (conceptText == null) {
            return "redirect:/admin/text/list";
        }
        model.addAttribute("conceptTextFormData", conceptText);
        return "admin/text/edit";
    }

    @PostMapping(value = "/admin/text/{id}/edit")
    public String updateTextData(@PathVariable("id") Long id, Model model,
            @ModelAttribute("conceptTextFormData") @Valid ConceptText updatedForm, BindingResult bindingResult,
            Principal principal, RedirectAttributes redirectAttrs) throws CannotFindConceptTextException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("conceptTextFormData", updatedForm);
            return "admin/text/edit";
        }
        conceptTextService.updateText(updatedForm, principal.getName(), id);
        return "redirect:/admin/text/list?page=1";
    }
}

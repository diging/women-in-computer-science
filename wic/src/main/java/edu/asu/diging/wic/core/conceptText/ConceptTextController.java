package edu.asu.diging.wic.core.conceptText;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;
import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;

@Controller
public class ConceptTextController {
	
	@Autowired
	private IConceptTextService iConceptTextService;
	
	@RequestMapping(value="/admin/import/addConceptText", method=RequestMethod.GET)
    public String addText(Model model) {
        return "admin/import/addConceptText";
    }
		
	@RequestMapping(value="/admin/import/addConceptTextData", method=RequestMethod.POST)
    public String addTextData(@RequestParam(value="conceptId") String conceptId, 
    		@RequestParam(value="title") String title ,
    		@RequestParam(value="author") String author ,
    		@RequestParam(value="paragraph_text") String paragraph_text ,
    		Principal principal, RedirectAttributes redirectAttrs) {

        ConceptText dataObj = new ConceptText();
        dataObj.setConceptId(conceptId);
        dataObj.setTitle(title);
        dataObj.setAuthor(author);
        dataObj.setText(paragraph_text);
        dataObj.setAddedOn(new Date().getTime()+"");
        dataObj.setAddedBy(principal.getName());
        iConceptTextService.addText(dataObj);
        return "admin/import/addConceptText";
    }

	@RequestMapping(value="/admin/import/showConceptText", method=RequestMethod.GET)
	public String showAllText(Model model) {
		
		List<ConceptText> allConceptText = iConceptTextService.showAllText();
		model.addAttribute("allConceptText",allConceptText);
		return "admin/import/showConceptText";
	}
	
	@RequestMapping(value="/admin/import/deleteConceptText/{id}", method=RequestMethod.GET)
	public String deleteText(@PathVariable("id") String id, Model model) {
		
		iConceptTextService.deleteText(id);
		List<ConceptText> allConceptText = iConceptTextService.showAllText();
		model.addAttribute("allConceptText",allConceptText);
		return "admin/import/showConceptText";
	}
	
	@RequestMapping(value="/admin/import/updateConceptTextData", method=RequestMethod.POST)
    public ResponseEntity<String> updateTextData(@RequestParam(value="id") String id, 
    		@RequestParam(value="text") String text ,
    		Principal principal, RedirectAttributes redirectAttrs) {
		
        iConceptTextService.updateText(id, text, principal.getName());
        return new ResponseEntity<>("result successful result", HttpStatus.OK);
    }

	@RequestMapping(value="/admin/import/editConceptTextView/{id}", method=RequestMethod.GET)
    public String editConceptTextView( @PathVariable("id") String id, Model model,Principal principal) {
		
		ConceptText dataObj = iConceptTextService.getConceptTextById(id);
		model.addAttribute("idOfData", dataObj.getId());
		model.addAttribute("title", dataObj.getTitle());
		model.addAttribute("text", dataObj.getText());
		model.addAttribute("conceptId", dataObj.getConceptId());
        return "admin/import/editConceptText";
    }
}

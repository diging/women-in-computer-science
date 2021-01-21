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
		
        System.out.println(conceptId);
        System.out.println(title);
        System.out.println(author);
        System.out.println(paragraph_text);
        ConceptText dataObj = new ConceptText();
        dataObj.setConceptId(conceptId);
        dataObj.setTitle(title);
        dataObj.setAuthor(author);
        dataObj.setText(paragraph_text);
        dataObj.setTimestamp(new Date().getTime()+"");
        iConceptTextService.addText(dataObj);
        return "admin/import/addConceptText";
    }
	
	@RequestMapping(value="/admin/import/getConceptText/{conceptId}", method=RequestMethod.GET)
    public String showText(@PathVariable("conceptId") String conceptId, Model model) {
        
//		List<ConceptText> allConceptIdData = iConceptTextService.allTextOfConcept(conceptId);
//		for(ConceptText tmp : allConceptIdData) {
//			System.out.println(tmp.getText());
//		}
//        return "admin/import/addConceptText";
		return "";
    }
	
	@RequestMapping(value="/admin/import/showConceptText", method=RequestMethod.GET)
	public String showAllText(Model model) {
		
		List<ConceptText> allConceptText = iConceptTextService.showAllText();
		for(ConceptText k : allConceptText) {
			System.out.println(k.getText());
		}
//		model.addAttribute("conceptText",allConceptText);
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

        iConceptTextService.updateText(id, text);
        return new ResponseEntity<>("result successful result", HttpStatus.OK);
    }
	
	@RequestMapping(value="/admin/import/editConceptText", method=RequestMethod.POST)
    public String editTextData(@RequestParam(value="id") String id, 
    		@RequestParam(value="text") String text ,
    		@RequestParam(value="conceptId") String conceptId ,
    		@RequestParam(value="title") String title ,
    		Principal principal, RedirectAttributes redirectAttrs, Model model) {
		System.out.println(id);
        model.addAttribute("id", id);
        redirectAttrs.addFlashAttribute("id", id);
        //editConceptTextView(model);
        return "redirect:/admin/import/editConceptTextView";
    }
	
	@RequestMapping(value="/admin/import/editConceptTextView/{id}", method=RequestMethod.GET)
    public String editConceptTextView( @PathVariable("id") String id, Model model) {
		
		ConceptText data = iConceptTextService.getConceptTextById(id);
		model.addAttribute("idOfData", data.getId());
		model.addAttribute("title", data.getTitle());
		model.addAttribute("text", data.getText());
		model.addAttribute("conceptId", data.getConceptId());
		
        return "admin/import/editConceptText";
    }
}

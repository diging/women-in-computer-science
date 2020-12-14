package edu.asu.diging.wic.core.conceptText;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;
import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;

@Controller
public class ConceptTextController {
	
	@Autowired
	private IConceptTextService iConceptTextService;
	
	@RequestMapping(value="/admin/import/addConceptText", method=RequestMethod.GET)
    public String addText(Model model) {
        
		
//		conceptText.setConceptId("CONRsyWzi5zJ4st");
//		conceptText.setText("xyz");
//		iConceptTextService.addText(conceptText);
        return "admin/import/addConceptText";
    }
	
	@RequestMapping(value="/admin/import/addConceptTextData", method=RequestMethod.POST)
    public ResponseEntity<String> addTextData(@RequestParam(value="one") String conceptId, Principal principal, RedirectAttributes redirectAttrs) {
        System.out.println(conceptId);
        ResponseEntity<String> a = new ResponseEntity<String>(HttpStatus.ACCEPTED);
        return a;
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
}

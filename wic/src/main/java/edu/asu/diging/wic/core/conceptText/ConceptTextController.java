package edu.asu.diging.wic.core.conceptText;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.wic.core.conceptText.model.ConceptText;
import edu.asu.diging.wic.core.conceptText.service.IConceptTextService;

@Controller
public class ConceptTextController {
	
	@Autowired
	private IConceptTextService iConceptTextService;
	
	@RequestMapping(value="/admin/import/addConceptText", method=RequestMethod.GET)
    public String addText(Model model) {
        
		ConceptText conceptText = new ConceptText();
		conceptText.setConceptId("CONRsyWzi5zJ4st");
		conceptText.setText("xyz");
		iConceptTextService.addText(conceptText);
        return "admin/import/conceptText";
    }

	@RequestMapping(value="/admin/import/getConceptText/{conceptId}", method=RequestMethod.GET)
    public String showText(@PathVariable("conceptId") String conceptId, Model model) {
        
		List<ConceptText> allConceptIdData = iConceptTextService.allTextOfConcept(conceptId);
		for(ConceptText tmp : allConceptIdData) {
			System.out.println(tmp.getText());
		}
        return "admin/import/conceptText";
    }
}

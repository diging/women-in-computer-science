package edu.asu.diging.wic.web.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.wic.core.dataimport.IDataImporter;
import edu.asu.diging.wic.core.model.IConcept;

@Controller
public class ImportPersonController {
    
    @Autowired
    private IConceptpowerConnector cpConnector;
    
    @Autowired
    private IDataImporter dataImporter;

    @RequestMapping(value="/admin/import/person", method=RequestMethod.GET)
    public String showImportPage() {
        return "admin/import/person";
    }
    
    @RequestMapping(value="/admin/import/person", method=RequestMethod.POST)
    public String importPerson(@RequestParam(value="id") String conceptId, Principal principal, RedirectAttributes redirectAttrs) {
        dataImporter.importPerson(conceptId, principal.getName());
        redirectAttrs.addFlashAttribute("alert_type", "success");
        redirectAttrs.addFlashAttribute("show_alert", true);
        redirectAttrs.addFlashAttribute("alert_msg", "Data import has been started.");
        return "redirect:/admin/import/person";
    }
    
    @RequestMapping(value="/admin/import/person/search", method=RequestMethod.GET)
    public ResponseEntity<List<IConcept>> search(@RequestParam String searchTerm) {
        return new ResponseEntity<>(cpConnector.search(searchTerm), HttpStatus.OK);
    }
}

package edu.asu.diging.wic.web.admin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.conceptpower.impl.ConceptpowerConcept;
import edu.asu.diging.wic.core.dataimport.impl.TransactionalImportManager;
import edu.asu.diging.wic.core.dataimport.model.ImportProgress;
import edu.asu.diging.wic.core.model.IConcept;

@Controller
public class ListImportsController {

    @Autowired
    private TransactionalImportManager importManager;
    
    @Autowired
    private IConceptpowerCache conceptpowerCache;
    
    @RequestMapping(value="/admin/import/list", method=RequestMethod.GET)
    public String list(Model model) {
        List<ImportProgress> imports = importManager.getAllProgresses();
        Collections.reverse(imports);
        model.addAttribute("imports", imports);
        
        Map<String, IConcept> concepts = new HashMap<>();
        imports.forEach(i -> {
            if (!concepts.containsKey(i.getConceptId())) {
                concepts.put(i.getConceptId(), conceptpowerCache.getConceptById(i.getConceptId()));
            }
        });
        model.addAttribute("concepts", concepts);
        
        return "admin/import/list";
    }
}

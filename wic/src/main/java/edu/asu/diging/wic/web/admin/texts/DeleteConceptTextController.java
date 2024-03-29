package edu.asu.diging.wic.web.admin.texts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
public class DeleteConceptTextController {

    @Autowired
    private IConceptTextService conceptTextService;

    @RequestMapping(value = "/admin/text/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteText(@PathVariable("id") Long id, Model model) {
        conceptTextService.deleteText(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

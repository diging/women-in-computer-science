package edu.asu.diging.wic.web.admin.texts;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Controller
@PropertySource("classpath:config.properties")
public class ShowConceptTextController {

    @Autowired
    private IConceptTextService conceptTextService;

    @Value("${itemsPerPage}")
    private Integer itemsPerPage;

    private static final String ASC_DIR = "ASC";

    @RequestMapping(value = "/admin/text/list", method = RequestMethod.GET)
    public String findAll(Model model, Principal principal,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "title", required = false, value = "sort") String sort,
            @RequestParam(defaultValue = ASC_DIR, required = false, value = "order") String order) {
        Direction dir;
        try {
            dir = Direction.fromString(order);
        } catch (IllegalArgumentException e) {
            dir = Direction.fromString(ASC_DIR);
        }
        Page<ConceptText> conceptTexts = conceptTextService.findAll(page, itemsPerPage, sort, dir);
        model.addAttribute("conceptTexts", conceptTexts.getContent());
        model.addAttribute("totalPages", conceptTexts.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        return "admin/text/list";
    }

}

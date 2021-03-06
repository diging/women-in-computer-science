package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.asu.diging.wic.core.conceptpower.IConceptMapper;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IConceptType;
import edu.asu.diging.wic.core.model.impl.Concept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

@Component
public class ConceptMapper implements IConceptMapper {
	
    /* (non-Javadoc)
    * @see edu.asu.diging.grazer.core.conceptpower.impl.IConceptMapper#mapConceptpowerConceptToConcept
    */
    @Override
    public IConcept mapConceptpowerConceptToConcept(ConceptpowerConcept conceptpowerConcept) {
        IConcept concept = new Concept();
        IConceptType conceptType = conceptpowerConcept.getType();
		
        //ID
        concept.setId(conceptpowerConcept.getId());
        
        //URI
        concept.setUri(conceptpowerConcept.getConceptUri());
        
        //WORD
        concept.setWord(conceptpowerConcept.getLemma());
        
        //POS
        concept.setPos(conceptpowerConcept.getPos());
        
        //DESC
        concept.setDescription(conceptpowerConcept.getDescription());
        
        //CONCEPTLIST
        concept.setConceptList(conceptpowerConcept.getConceptList());
        
        //TYPEID
        concept.setTypeId(conceptType != null && conceptType.getUri() != null ? conceptType.getUri()  : "");
        
        //ALTERNATIVEURIS
        List<ConceptpowerAlternativeId> altIds = conceptpowerConcept.getAlternativeIds();
        List<String> ids = new ArrayList<>();
        altIds.forEach(id -> ids.add(id.getConceptUri()));
        concept.setAlternativeUris(ids);
        
        //CREATORID
        concept.setCreatorId(conceptpowerConcept.getCreatorId());
        
        //EQUALTO
        if (conceptpowerConcept.getEqualTo() != null) {
            concept.setEqualTo(conceptpowerConcept.getEqualTo());
        } else {
            concept.setEqualTo(new ArrayList<>());
        }
        
        if (conceptpowerConcept.getSimilarTo() != null) {
            concept.setSimilarTo(Arrays.asList(conceptpowerConcept.getSimilarTo().split(",")));
        } else {
            concept.setSimilarTo(new ArrayList<>());
        }
        
        //WORDNETIDS
        if (conceptpowerConcept.getWordnetId() != null) {
            concept.setWordnetIds(conceptpowerConcept.getWordnetId());
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }
        
        //SETTYPE
        if (conceptType != null) {
            IConceptType type = new ConceptType();
            type.setUri(conceptType.getUri());
            type.setId(conceptType.getId());
            type.setDescription("");
            type.setName(conceptType.getName());
            concept.setType(type);
        }
        
        return concept;
    }
}
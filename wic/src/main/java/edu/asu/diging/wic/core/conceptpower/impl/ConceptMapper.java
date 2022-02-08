package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.asu.diging.wic.core.conceptpower.IConceptEntry;
import edu.asu.diging.wic.core.conceptpower.IConceptEntryId;
import edu.asu.diging.wic.core.conceptpower.IConceptMapper;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IConceptType;
import edu.asu.diging.wic.core.model.impl.Concept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

@Component
public class ConceptMapper implements IConceptMapper {

    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.conceptpower.IConceptMapper#mapConceptpowerConceptToConcept(edu.asu.diging.wic.core.conceptpower.IConceptEntry)
     */
    @Override
    public IConcept mapConceptpowerConceptToConcept(IConceptEntry conceptEntry) {
        IConcept concept = new Concept();
        IConceptType conceptType = conceptEntry.getType();

        concept.setId(conceptEntry.getConceptId());

        concept.setUri(conceptEntry.getConceptUri());

        concept.setWord(conceptEntry.getLemma());

        concept.setPos(conceptEntry.getPos());

        concept.setDescription(conceptEntry.getDescription());

        concept.setConceptList(conceptEntry.getConceptList());

        concept.setTypeId(conceptType != null && conceptType.getUri() != null ? conceptType.getUri() : "");

        List<IConceptEntryId> altIds = conceptEntry.getAlternativeIds();
        List<String> ids = new ArrayList<>();
        altIds.forEach(id -> ids.add(id.getConceptUri()));
        concept.setAlternativeUris(ids);

        concept.setCreatorId(conceptEntry.getCreatorId());

        if (conceptEntry.getEqualTo() != null) {
            concept.setEqualTo(conceptEntry.getEqualTo());
        } else {
            concept.setEqualTo(new ArrayList<>());
        }

        if (conceptEntry.getSimilarTo() != null) {
            concept.setSimilarTo(conceptEntry.getSimilarTo());
        } else {
            concept.setSimilarTo(new ArrayList<>());
        }

        if (conceptEntry.getWordnetIds() != null) {
            concept.setWordnetIds(conceptEntry.getWordnetIds());
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }

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
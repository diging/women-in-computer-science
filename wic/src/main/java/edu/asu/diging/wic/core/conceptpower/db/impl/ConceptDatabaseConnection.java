package edu.asu.diging.wic.core.conceptpower.db.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IConceptType;
import edu.asu.diging.wic.core.model.impl.Concept;
import edu.asu.diging.wic.core.model.impl.ConceptType;

@Component
@Transactional
public class ConceptDatabaseConnection implements IConceptDatabaseConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EntityManager em;

    @Override
    public IConcept getConcept(String id) {
        Object objConcept = null;

        Query query = em.createQuery("SELECT c from Concept c WHERE c.id = :id");
        query.setParameter("id", id);
        List<?> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            // there shouldn't be more than one, but if there is just take the
            // first one
            objConcept = results.get(0);
        }

        if (objConcept == null) {
            return null;
        }

        IConcept concept = (IConcept) objConcept;
        if (concept.getTypeId() != null) {
            Object objType = em.find(ConceptType.class, concept.getTypeId());
            if (objType != null) {
                concept.setType((IConceptType) objType);
            }
        }
        return concept;
    }

    @Override
    public IConcept getConceptByUri(String uri) {
        Object objConcept = em.find(Concept.class, uri);

        // let's check if concept uses a different main id
        if (objConcept == null) {
            Query query = em.createQuery("SELECT c from Concept c WHERE :uri in elements(c.alternativeUris)");
            query.setParameter("uri", uri);
            List<?> results = query.getResultList();
            if (results != null && !results.isEmpty()) {
                // there shouldn't be more than one, but if there is just take
                // the first one
                objConcept = results.get(0);
            }
        }

        if (objConcept == null) {
            return null;
        }

        IConcept concept = (IConcept) objConcept;
        if (concept.getTypeId() != null) {
            Object objType = em.find(ConceptType.class, concept.getTypeId());
            if (objType != null) {
                concept.setType((IConceptType) objType);
            }
        }

        return concept;
    }

    @Override
    public void createOrUpdate(IConcept concept) {
        Object objConcept = em.find(Concept.class, concept.getUri());
        // if concept exists, let's update it
        if (objConcept == null || isDifferent(concept, (IConcept) objConcept)) {
            logger.debug((objConcept == null ? "Adding " : "Updating: ") + concept.getUri());
            concept.setLastUpdated(OffsetDateTime.now());

            if (objConcept != null) {
                em.remove(objConcept);
            }
            em.persist(concept);
        }

        // update type if there is one
        if (concept.getTypeId() != null && !concept.getTypeId().trim().isEmpty()) {
            IConceptType type = getType(concept.getTypeId());
            if (type == null || isDifferent(concept.getType(), type)) {
                if (type != null) {
                    em.detach(type);
                }
                if (concept.getType() != null) {
                    em.persist(concept.getType());
                }
            }
        }
    }

    @Override
    public void deleteConcept(String uri) {
        Object concept = em.find(Concept.class, uri);
        if (concept != null) {
            em.remove(concept);
        }
    }

    @Override
    public IConceptType getType(String id) {
        Object objType = em.find(ConceptType.class, id);
        if (objType != null) {
            return (IConceptType) objType;
        }
        return null;
    }

    @Override
    public List<ConceptType> getAllConceptTypes() {
        TypedQuery<ConceptType> query = em.createQuery("from ConceptType", ConceptType.class);
        return query.getResultList();
    }

    private boolean isDifferent(IConcept concept1, IConcept concept2) {
        if (concept1.getAlternativeUris().size() != concept2.getAlternativeUris().size()) {
            return true;
        }
        List<String> altIds1 = new ArrayList<>(concept1.getAlternativeUris());
        List<String> altIds2 = new ArrayList<>(concept2.getAlternativeUris());
        Collections.sort(altIds1);
        Collections.sort(altIds2);
        if (!altIds1.equals(altIds2)) {
            return true;
        }
        if (!concept1.getConceptList().equals(concept2.getConceptList())) {
            return true;
        }
        if (!concept1.getDescription().equals(concept2.getDescription())) {
            return true;
        }
        if (concept1.getEqualTo().size() != concept2.getEqualTo().size()) {
            return true;
        }

        List<String> equalTo1 = new ArrayList<>(concept1.getEqualTo());
        List<String> equalTo2 = new ArrayList<>(concept2.getEqualTo());
        Collections.sort(equalTo1);
        Collections.sort(equalTo2);
        if (!equalTo1.equals(equalTo2)) {
            return true;
        }
        if (!concept1.getId().equals(concept2.getId())) {
            return true;
        }
        if (!concept1.getPos().equals(concept2.getPos())) {
            return true;
        }
        if (!concept1.getTypeId().equals(concept2.getTypeId())) {
            return true;
        }
        if (!concept1.getUri().equals(concept2.getUri())) {
            return true;
        }
        if (!concept1.getWord().equals(concept2.getWord())) {
            return true;
        }
        if (concept1.getWordnetIds().size() != concept2.getWordnetIds().size()) {
            return true;
        }
        List<String> wordnetIds1 = new ArrayList<>(concept1.getWordnetIds());
        List<String> wordnetIds2 = new ArrayList<>(concept2.getWordnetIds());
        Collections.sort(wordnetIds1);
        Collections.sort(wordnetIds2);
        if (!wordnetIds1.equals(wordnetIds2)) {
            return true;
        }
        return false;
    }

    private boolean isDifferent(IConceptType type1, IConceptType type2) {
        if (type1 == null && type2 == null) {
            return false;
        }
        if (type1 == null || type2 == null) {
            return true;
        }
        if (!type1.getId().equals(type2.getId())) {
            return true;
        }
        if (!type1.getDescription().equals(type2.getDescription())) {
            return true;
        }
        if (!type1.getName().equals(type2.getName())) {
            return true;
        }
        if (!type1.getUri().equals(type2.getUri())) {
            return true;
        }
        return false;
    }

}
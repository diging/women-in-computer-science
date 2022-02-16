package edu.asu.diging.wic.core.conceptpower.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerConnector;
import edu.asu.diging.wic.core.model.IConcept;

@Service
@PropertySource("classpath:config.properties")
public class ConceptpowerConnector implements IConceptpowerConnector {

    @Autowired
    private ConceptMapper conceptMapper;

    @Value("${conceptpower.url}")
    private String conceptpowerUrl;

    @Value("${conceptpower.concept.endpoint}")
    private String conceptEndpoint;
    
    @Value("${conceptpower.search.endpoint}")
    private String searchEndpoint;

    private RestTemplate restTemplate;

    public ConceptpowerConnector() {
        restTemplate = new RestTemplate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.diging.grazer.core.conceptpower.impl.IConceptpowerConnector#
     * getConcept(java.lang.String)
     */
    @Override
    @Cacheable(value = "concepts")
    public IConcept getConcept(String id) {
        ConceptpowerReply concepts = restTemplate
                .getForObject(String.format("%s%s%s", conceptpowerUrl, conceptEndpoint, id), ConceptpowerReply.class);
        if (concepts.getConceptEntries() != null && !concepts.getConceptEntries().isEmpty()) {
            ConceptEntry cpc = concepts.getConceptEntries().get(0);
            return conceptMapper.mapConceptpowerConceptToConcept(cpc);
        }
        return null;
    }
    

    @Override
    public List<IConcept> search(String searchTerm) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(
                Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        HttpEntity<?> entity = new HttpEntity<Object>(requestHeaders);
        
        ResponseEntity<ConceptpowerConcepts> response = restTemplate.exchange(
                String.format("%s%sword=%s", conceptpowerUrl, searchEndpoint, searchTerm),
                HttpMethod.GET, entity, ConceptpowerConcepts.class);
        
        List<IConcept> results = new ArrayList<>();
        ConceptpowerConcepts concepts = response.getBody();
        if (concepts.getConceptEntries() != null) {
            concepts.getConceptEntries().forEach(c -> results.add(conceptMapper.mapConceptpowerConceptToConcept(c)));
        }
             
        return results;
    }
}

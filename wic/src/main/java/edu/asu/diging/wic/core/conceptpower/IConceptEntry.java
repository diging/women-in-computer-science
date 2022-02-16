package edu.asu.diging.wic.core.conceptpower;

import java.util.List;

import edu.asu.diging.wic.core.model.IConceptType;

public interface IConceptEntry {
    
    String getConceptId();
    
    String getConceptUri();
    
    String getLemma();
    
    String getPos();
    
    String getDescription();
    
    String getConceptList();
    
    IConceptType getType();
    
    String getCreatorId();
    
    List<String> getEqualTo();
    
    List<String> getSimilarTo();
    
    List<String> getWordnetIds();
    
    <T extends IConceptEntryId> List<T> getAlternativeIds();
    
}

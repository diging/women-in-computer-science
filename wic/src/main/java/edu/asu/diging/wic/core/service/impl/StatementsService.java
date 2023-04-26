package edu.asu.diging.wic.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.conceptpower.IConceptpowerCache;
import edu.asu.diging.wic.core.dataimport.IImportedConceptDBConnection;
import edu.asu.diging.wic.core.graphs.IGraphDBConnection;
import edu.asu.diging.wic.core.model.IConcept;
import edu.asu.diging.wic.core.model.IImportedConcept;
import edu.asu.diging.wic.core.service.IStatementService;

@Service
@Transactional
public class StatementsService implements IStatementService {
    
    @Autowired
    private IImportedConceptDBConnection importedConceptDbConnection;
    
    @Autowired
    private IConceptpowerCache cpCache;
    
    /* (non-Javadoc)
     * @see edu.asu.diging.wic.core.service.impl.IStatementService#getImportedConcepts()
     */
    @Override
    public List<IConcept> getImportedConcepts() {
        List<IImportedConcept> importedConcepts = importedConceptDbConnection.list();
        List<IConcept> concepts = new ArrayList<>();
        importedConcepts.forEach(c -> concepts.add(cpCache.getConceptById(c.getConceptId())));
        return concepts;
    }
}

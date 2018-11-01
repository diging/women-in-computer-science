package edu.asu.diging.wic.core.dataimport.db.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.diging.wic.core.dataimport.model.ImportProgress;

@Component
public class ImportProgressDbConnection {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    public String store(ImportProgress progress) {
        return (String) sessionFactory.getCurrentSession()
                .save(progress);
    }
    
    public void update(ImportProgress progress) {
        sessionFactory.getCurrentSession().update(progress);
        sessionFactory.getCurrentSession().flush();
    }

    public ImportProgress get(String id) {
        return (ImportProgress) sessionFactory.getCurrentSession()
                .get(ImportProgress.class, id);
    }
}

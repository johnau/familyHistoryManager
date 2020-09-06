package tech.jmcs.fhm;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author John
 */
public class DatabaseProducer {

    @PersistenceContext(unitName="fhm_pu")
    private EntityManager familyHistoryContext;

    @PersistenceUnit(unitName="fhm_pu")
    private EntityManagerFactory familyHistoryPersistenceUnit;

    @Produces
    @MainDatabase
    public EntityManager getContext() {
        return familyHistoryContext;
    }

    @Produces
    @MainDatabase
    public EntityManagerFactory getPersistenceUnit() {
        return familyHistoryPersistenceUnit;
    }

}
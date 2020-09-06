package tech.jmcs.fhm.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
import tech.jmcs.fhm.model.FamilyRelationship;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class FamilyRelationshipFacade extends AbstractFacade<FamilyRelationship> implements FamilyRelationshipFacadeLocal {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyRelationshipFacade.class);

    @Inject
    @MainDatabase
    private EntityManager em;

    public FamilyRelationshipFacade() {
        super(FamilyRelationship.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Boolean okToCreate(FamilyRelationship newEntity) {
        return true;
    }
}

package tech.jmcs.fhm.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
import tech.jmcs.fhm.model.FamilyMemberBioSection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class FamilyMemberBioSectionFacade extends AbstractFacade<FamilyMemberBioSection> implements FamilyMemberBioSectionFacadeLocal {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyMemberBioSectionFacade.class);

    @Inject
    @MainDatabase
    private EntityManager em;

    public FamilyMemberBioSectionFacade() {

        super(FamilyMemberBioSection.class);

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Boolean okToCreate(FamilyMemberBioSection newEntity) {
        return true;
    }

}

package tech.jmcs.fhm.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
import tech.jmcs.fhm.jsf.CreateFamilyMemberBacking;
import tech.jmcs.fhm.model.FamilyMember;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FamilyMemberFacade extends AbstractFacade<FamilyMember> implements FamilyMemberFacadeLocal {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyMemberFacade.class);

    @Inject
    @MainDatabase
    private EntityManager em;

    public FamilyMemberFacade() {

        super(FamilyMember.class);

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Boolean okToCreate(FamilyMember newEntity) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FamilyMember> findByName(String name) {
        if (name == null || name.isEmpty()) {
            LOG.debug("Find By Name: No name provided!");
            return null;
        }

        Long countByName = (Long) em.createNamedQuery("FamilyMember.countByName")
                .setParameter("name", name)
                .getSingleResult();

        if (countByName == 0) {
            return new ArrayList<FamilyMember>();
        }
        List existing = em.createNamedQuery("FamilyMember.findByName")
                .setParameter("name", name)
                .getResultList();

        try {
            List<FamilyMember> _existing = (List<FamilyMember>) existing;

            LOG.debug("Found [{}] existing Family Members with Name '{}'", _existing.size(), name);

            return _existing;
        } catch (ClassCastException ce) {
            return new ArrayList<FamilyMember>();
        }
    }

    public List<FamilyMember> findByFullName(String firstname, String otherNames, String lastname) {
        if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty()) {
            LOG.debug("Find By Full Name: Need to provide Firstname and Lastname");
            return null;
        }

        Long countByName = (Long) em.createNamedQuery("FamilyMember.countByFullName")
                .setParameter("firstname", firstname)
                .setParameter("otherNames", otherNames)
                .setParameter("lastname", lastname)
                .getSingleResult();

        if (countByName == 0) {
            return new ArrayList<FamilyMember>();
        }
        List existing = em.createNamedQuery("FamilyMember.findByFullName")
                .setParameter("firstname", firstname)
                .setParameter("otherNames", otherNames)
                .setParameter("lastname", lastname)
                .getResultList();

        try {
            List<FamilyMember> _existing = (List<FamilyMember>) existing;

            LOG.debug("Found [{}] existing Family Members with Name '{} {} {}'", _existing.size(), firstname, otherNames, lastname);

            return _existing;
        } catch (ClassCastException ce) {
            return new ArrayList<FamilyMember>();
        }

    }
}

package tech.jmcs.fhm.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
import tech.jmcs.fhm.model.Group;
import tech.jmcs.fhm.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class GroupFacade extends AbstractFacade<Group> implements GroupFacadeLocal {
    private static final Logger LOG = LoggerFactory.getLogger(GroupFacade.class);

    @Inject
    @MainDatabase
    private EntityManager em;

    public GroupFacade() {
        super(Group.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Boolean okToCreate(Group newEntity) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Group findByName(String groupName) {
        List<Group> groups = (List<Group>) em.createNamedQuery("Group.findByName")
                .setParameter("name", groupName)
                .getResultList();

        if (groups.isEmpty()) {
            return null;
        } else {
            return groups.get(0);
        }
    }
}
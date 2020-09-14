package tech.jmcs.fhm.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
import tech.jmcs.fhm.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {
    private static final Logger LOG = LoggerFactory.getLogger(UserFacade.class);

    @Inject
    @MainDatabase
    private EntityManager em;

    public UserFacade() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Boolean okToCreate(User newEntity) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findByUsername(String username) {
        List<User> users = (List<User>) em.createNamedQuery("User.findByUsername")
                .setParameter("username", username)
                .getResultList();

        if (users.isEmpty()) {
            return null;
        } else if (users.size() > 1) {
            LOG.error("Database Error: There are more than 1 user with the same username.  This username will not be usable until this issue is fixed.");
            return null;
        } else {
            return users.get(0);
        }
    }
}
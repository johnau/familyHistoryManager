package tech.jmcs.fhm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.GroupFacadeLocal;
import tech.jmcs.fhm.ejb.facade.UserFacadeLocal;
import tech.jmcs.fhm.model.Group;
import tech.jmcs.fhm.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class LifeCycle {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCycle.class);

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private GroupFacadeLocal groupFacade;

    @PostConstruct
    /**
     * Checks the Database for the admin user, creates if required.
     */
    public void init() {
        User adminUser = userFacade.findByUsername("admin");
        Group adminGroup = groupFacade.findByName("admin");

        if (adminUser == null) {
            User _adminUser = createAdminUser();

            if (adminGroup == null) {
                createAdminGroup(_adminUser);
            } else {
                LOG.info("The 'admin' group already existed");
            }

        } else {
            LOG.info("The 'admin' user already existed.");
        }

    }

    private User createAdminUser() {
        LOG.info("Creating the 'admin' user!");

        String hashedPassword;
        try {
            hashedPassword = PasswordHash.generatePasswordHash("admin");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOG.error("Password Hashing Error!");
            throw new RuntimeException(e);
        }

        User adminUser = new User("admin", hashedPassword);
        userFacade.create(adminUser);

        return adminUser;
    }

    private Group createAdminGroup(User adminUser) {
        LOG.info("The 'admin' group did not exist, creating...");

        Group adminGroup = new Group("admin");
        List<User> adminUsers = new ArrayList();
        adminUsers.add(adminUser);

        adminGroup.setUsers(adminUsers);
        groupFacade.create(adminGroup);

        adminUser.setGroup(adminGroup);
        userFacade.edit(adminUser);

        return adminGroup;
    }
}

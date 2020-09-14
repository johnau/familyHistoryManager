package tech.jmcs.fhm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.UserFacadeLocal;
import tech.jmcs.fhm.model.Group;
import tech.jmcs.fhm.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {
    private static final Logger LOG = LoggerFactory.getLogger(CustomIdentityStore.class);

    @EJB
    private UserFacadeLocal userFacade;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        LOG.info("Validating user credentials: {} {}", credential.getCaller(), credential.getPasswordAsString());
        User user = userFacade.findByUsername(credential.getCaller());

        if (user == null) {
            LOG.debug("No user exists with the username: {}", credential.getCaller());
            return INVALID_RESULT;
        }

        boolean validLogin = false;
        try {
            validLogin = PasswordHash.validatePassword(credential.getPasswordAsString(), user.getPasswordHash());

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        if (!validLogin) {
            LOG.debug("Password was incorrect for username: {}", credential.getCaller());
            return INVALID_RESULT;
        }

        Set<String> groups = new HashSet<String>();
        Group userGroup = user.getGroup();
        if (userGroup == null) {
            LOG.warn("User {} is not assigned a group, Guest group will be set.", user.getUsername());
            groups.add("GUEST");
        } else {
            LOG.debug("User {} is in group: {}", user.getUsername(), user.getGroup().getGroupName());
            groups.add(user.getGroup().getGroupName().toUpperCase());
        }

        return new CredentialValidationResult(user.getUsername(), groups);
    }

//    @Override
//    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
//        LOG.info("Getting groups");
//
//        User user = userFacade.findByUsername(validationResult.getCallerPrincipal().getName());
//        Set<String> groups = new HashSet<String>();
//        groups.add(user.getGroup().getGroupName());
//        LOG.info("User group: {}", user.getGroup().getGroupName());
//
//        return groups;
//    }
}

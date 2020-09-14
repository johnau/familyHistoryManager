package tech.jmcs.fhm.jsf.security;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author John
 */
@Named
@RequestScoped
@Deprecated
public class LogoutBacking {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutBacking.class.getName());

    @Inject
    private ExternalContext externalContext;

    public String logout() {
        String loggedUser = externalContext.getUserPrincipal().getName();
        externalContext.invalidateSession();
        LOG.debug("Logged out user: '{}'", loggedUser);

        String redirectPath = externalContext.getRequestContextPath() + "/index.xhtml";
        try {
            externalContext.redirect(redirectPath);
            LOG.debug("Redirect to: {}", redirectPath);
        } catch (IOException ex) {
            LOG.warn("Could not redirect");
        }

        return redirectPath;
    }

}
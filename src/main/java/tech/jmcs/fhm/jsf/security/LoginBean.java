package tech.jmcs.fhm.jsf.security;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;

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
public class LoginBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(LoginBean.class);
    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    @NotEmpty
    @Size(min = 3, message = "Please provide a valid Username or e-mail")
    private String username;

    @NotEmpty
    @Size(min = 4, message = "Password must have at least 4 characters")
    private String password;

    public void login() throws IOException
    {
        LOG.debug("Login attempt for user: {}", username);
        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case NOT_DONE:
            case SEND_FAILURE:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed...", null));
                break;
            case SUCCESS:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login success!", null));

                String redirectPath = externalContext.getRequestContextPath()+ "/index.xhtml";

                LOG.debug("Logged In! Redirect to '{}'", redirectPath);
                externalContext.redirect(redirectPath);
                break;
        }

//        return "login";
    }

    private AuthenticationStatus continueAuthentication()
    {
        LOG.debug("Auth attempt for user: {}", username);
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams()
                        .credential(new UsernamePasswordCredential(username, password))
        );
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
        LOG.debug("Set username: {}", username);
    }

}
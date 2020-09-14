/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.jmcs.fhm;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author John
 */
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml?faces-redirect=true",
                useForwardToLogin = false
            )
)

//@DatabaseIdentityStoreDefinition(
//        dataSourceLookup = "java:comp/env/jdbc/securityDS",
//        callerQuery = "select password_hash from fhm_user where username = ?",
//        groupsQuery = "select group from groups where username = ?",
//        priority=30)

//@DatabaseIdentityStoreDefinition(
//        callerQuery = "#{'select password from fhm_user where username = ?'}",
//        groupsQuery = "select group_name from fhm_group where username = ?",
//        hashAlgorithm = Pbkdf2PasswordHash.class,
//        priorityExpression = "#{100}",
//        hashAlgorithmParameters = {
//                "Pbkdf2PasswordHash.Iterations=3072",
//                "${appConfig.dyna}"
//        }
//)
@ApplicationScoped
@Named
public class AppConfig {

    public String[] getDyna() {
        return new String[]{"Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512", "Pbkdf2PasswordHash.SaltSizeBytes=64"};
    }

}


package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.FamilyMemberFacadeLocal;
import tech.jmcs.fhm.model.FamilyMember;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Named
@RequestScoped
public class CreateFamilyMemberBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CreateFamilyMemberBean.class);
    private static final long serialVersionUID = 1L;

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    @NotEmpty
    @Size(min = 1, message = "Please provide a first name")
    private String firstname;

    private String otherNames;

    @NotEmpty
    @Size(min = 1, message = "Please provide a last name")
    private String lastname;

    private Date dob;

    private Date dod;

    public void create() throws IOException {
        if (continueCreation()) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Created a new Family Member!", null));

            LOG.info("Created a new Family Member {} {}", firstname, lastname);

        } else {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not create new Family Member!", null));

            LOG.warn("Could not create new Family Member {} {}", firstname, lastname);
        }
    }

    private boolean continueCreation() {
        LOG.debug("Creating a new family member...");

        FamilyMember newMember = new FamilyMember();
        newMember.setFirstname(this.firstname);
        newMember.setOtherNames(this.otherNames);
        newMember.setLastname(this.lastname);

        familyMemberFacade.create(newMember);

        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDod() {
        return dod;
    }

    public void setDod(Date dod) {
        this.dod = dod;
    }
}

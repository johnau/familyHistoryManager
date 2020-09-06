package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.MainDatabase;
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
import java.util.Date;

@Named
@RequestScoped
public class CreateFamilyMemberBacking {
    private static final Logger LOG = LoggerFactory.getLogger(CreateFamilyMemberBacking.class);

    @NotEmpty
    @Size(min = 1, message = "Please provide a first name")
    private String firstName;

    private String otherNames;

    @NotEmpty
    @Size(min = 1, message = "Please provide a last name")
    private String lastName;

    private Date dob;

    private Date dod;

//    @Inject
//    @MainDatabase
//    private EntityManager em;

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    public void submit() throws IOException {
        if (continueCreation()) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Created a new Family Member!", null));

            LOG.info("Created a new Family Member {} {}", firstName, lastName);

        } else {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not create new Family Member!", null));

            LOG.warn("Could not create new Family Member {} {}", firstName, lastName);
        }

    }

    private boolean continueCreation() {
        LOG.debug("Creating a new family member...");

        FamilyMember newMember = new FamilyMember();
        newMember.setFirstName(this.firstName);
        newMember.setOtherNames(this.otherNames);
        newMember.setLastName(this.lastName);

        familyMemberFacade.create(newMember);

        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

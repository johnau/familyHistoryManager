package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.FamilyMemberFacadeLocal;
import tech.jmcs.fhm.model.FamilyMember;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@Named
@RequestScoped
public class FamilyMemberBacking {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyMemberBacking.class);

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @NotNull
    private Long id;  // Injection point from JSF View for 'id' URL parameter

    private FamilyMember familyMember;

    public FamilyMemberBacking() {
    }

    public void onLoad() {
        LOG.debug("Running on load method..");
//        this.familyMember = familyMemberFacade.find(this.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        LOG.debug("Looking up family member with id: {}", id);
        this.familyMember = familyMemberFacade.find(id);
        LOG.debug("Found family member: {}", familyMember);
    }

    public FamilyMember getFamilyMember() {
        LOG.info("Family member: {}", familyMember);
        return familyMember;
    }
}

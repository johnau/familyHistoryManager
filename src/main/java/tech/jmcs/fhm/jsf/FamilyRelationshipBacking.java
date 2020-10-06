package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.FamilyMemberFacadeLocal;
import tech.jmcs.fhm.ejb.facade.FamilyRelationshipFacadeLocal;
import tech.jmcs.fhm.model.FamilyMember;
import tech.jmcs.fhm.model.FamilyRelationship;
import tech.jmcs.fhm.model.RelationshipType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.List;

@Named
@RequestScoped
@Deprecated
public class FamilyRelationshipBacking {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyRelationshipBacking.class);

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @EJB
    private FamilyRelationshipFacadeLocal familyRelationshipFacade;

//    @Inject
//    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    @NotNull
    private Long id; // Injection point of FamilyMember 'a' from JSF view

    private FamilyMember a; // FamilyMember a has RelationshipType to FamilyMember b

    private RelationshipType relType;

    private FamilyMember b;

    private List<FamilyMember> allMembers;

    public FamilyRelationshipBacking() {
    }

    /**
     * Populate list of all FamilyMembers after Construct for JSF drop down menu items
     */
    @PostConstruct
    private void populateFamilyMembers() {
        this.allMembers = familyMemberFacade.findAll();
    }

    /**
     * Method accessed by JSF to populate drop-menu with RelationshipTypes
     * @return
     */
    public RelationshipType[] getRelationshipTypes() {
        return RelationshipType.values();
    }

    /**
     * Method accessed by JSF Metadata Action to load appropriate FamilyMember
     */
    public void onLoad() {
        LOG.info("Running the onload method");
//        LOG.info("Set Family member A as: #{}: {} {} {}", a.getId(), a.getFirstName(), a.getLastName(), a.getClass().getName());
    }

    /**
     * Method accessed by JSF Form to create a Family Relationship
     */
    public void submit() {
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Created a new relationship!", null));

        FamilyRelationship relationshipA = new FamilyRelationship();
        relationshipA.setA(this.a);
        relationshipA.setRelType(this.relType);
        relationshipA.setB(this.b);

        familyRelationshipFacade.create(relationshipA);

        LOG.debug("Created a relationship between #{} and #{}", this.a.getId(), this.b.getId());

        RelationshipType inverseRel = RelationshipType.valueOf(relType.getInverseNameString());

        FamilyRelationship relationshipB = new FamilyRelationship();
        relationshipB.setA(this.b);
        relationshipB.setRelType(inverseRel);
        relationshipB.setB(this.a);

        familyRelationshipFacade.create(relationshipB);

        LOG.debug("Created a relationship between #{} and #{}", this.b.getId(), this.a.getId());
    }

    /** Getters & Setters for JSF access to properties */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.a = familyMemberFacade.find(id);
        LOG.info("Set Family member A as: #{}: {} {} {}", a.getId(), a.getFirstname(), a.getLastname(), a.getClass().getName());
    }

    public FamilyMember getA() {
        return a;
    }

    public RelationshipType getRelType() {
        return relType;
    }

    public void setRelType(RelationshipType relType) {
        this.relType = relType;
        LOG.info("Set the relationship type (ENUM) as : {}", relType.toString());
    }

    public FamilyMember getB() {
        return b;
    }

    public void setB(FamilyMember b) {
        LOG.info("Set Family member B as: #{}: {} {} {}", b.getId(), b.getFirstname(), b.getLastname(), b.getClass().getName());
        this.b = b;
    }

    public List<FamilyMember> getAllMembers() {
        return allMembers;
    }

}

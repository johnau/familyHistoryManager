package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.FamilyMemberFacadeLocal;
import tech.jmcs.fhm.jsf.helper.SectionStringType;
import tech.jmcs.fhm.model.FamilyMember;
import tech.jmcs.fhm.model.FamilyMemberBioSection;
import tech.jmcs.fhm.model.InternalHyperLink;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class FamilyMemberBean {
    private static final Logger LOG = LoggerFactory.getLogger(FamilyMemberBean.class);

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @NotNull
    private Long id;  // Injection point from JSF View for 'id' URL parameter

    private FamilyMember familyMember;

    private List<Map<SectionStringType, Object>> processedBioSections;

    public FamilyMemberBean() {
    }

    public void onLoad() {
        LOG.debug("No action onLoad");
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

    public List<Map<SectionStringType, Object>> getProcessedBioSections() {
        if (processedBioSections == null) {
            processedBioSections = new ArrayList<>();
            List<FamilyMemberBioSection> bioSections = familyMember.getBioSections();
            bioSections.forEach( bs -> {
                String title = bs.getTitle();
                String content = bs.getContent();
                List<InternalHyperLink> links = bs.getLinks();
                Map<String, String> linksMap = bs.getLinkMap();


            });
        }

        return processedBioSections;
    }

    @RolesAllowed("ADMIN")
    public void beginCreateBioSection() {

    }

    @RolesAllowed("ADMIN")
    public void editFamilyMemberRelationships() {

    }


//    public List<FamilyMemberBioSection> getProcessedBioSections() {
//        return familyMember.getBioSections();
//    }
}

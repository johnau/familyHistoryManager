package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.FamilyMemberFacadeLocal;
import tech.jmcs.fhm.model.FamilyMember;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ListFamilyMembersBean {
    private static final Logger LOG = LoggerFactory.getLogger(ListFamilyMembersBean.class);

    private List<FamilyMember> allFamilyMembers;

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    public List<FamilyMember> getAllFamilyMembers() {
        this.allFamilyMembers = familyMemberFacade.findAll();

        LOG.debug("Found {} Family Members in the database.", this.allFamilyMembers.size());

        return this.allFamilyMembers;
    }
}

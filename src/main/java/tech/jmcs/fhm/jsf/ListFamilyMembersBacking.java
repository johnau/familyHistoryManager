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
public class ListFamilyMembersBacking {
    private static final Logger LOG = LoggerFactory.getLogger(ListFamilyMembersBacking.class);

    private List<FamilyMember> familyMembers;

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    public List<FamilyMember> getFamilyMembers() {
        this.familyMembers = familyMemberFacade.findAll();

        LOG.debug("Found {} Family Members in the database.", this.familyMembers.size());

        return this.familyMembers;
    }

}

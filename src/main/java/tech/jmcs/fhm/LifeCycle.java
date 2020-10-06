package tech.jmcs.fhm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.ejb.facade.*;
import tech.jmcs.fhm.helper.TestDataHelper;
import tech.jmcs.fhm.model.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Startup
public class LifeCycle {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCycle.class);

    private static final boolean POPULATE_TEST_DATA = true;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private GroupFacadeLocal groupFacade;

    @EJB
    private FamilyMemberFacadeLocal familyMemberFacade;

    @EJB
    private FamilyRelationshipFacadeLocal familyRelationshipFacade;

    @EJB
    private FamilyMemberBioSectionFacadeLocal familyMemberBioSectionFacade;

    @PostConstruct
    /**
     * Checks the Database for the admin user, creates if required.
     */
    public void init() {
        User adminUser = userFacade.findByUsername("admin");
        Group adminGroup = groupFacade.findByName("admin");

        if (adminUser == null) {
            User _adminUser = createAdminUser();

            if (adminGroup == null) {
                createAdminGroup(_adminUser);
            } else {
                LOG.info("The 'admin' group already existed");
            }

            // assume if admin user is not setup no pre-setup is done.
            if (POPULATE_TEST_DATA) {
                populateTestData();
            }

        } else {
            LOG.info("The 'admin' user already existed.");
        }

    }

    private User createAdminUser() {
        LOG.info("Creating the 'admin' user!");

        String hashedPassword;
        try {
            hashedPassword = PasswordHash.generatePasswordHash("admin");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOG.error("Password Hashing Error!");
            throw new RuntimeException(e);
        }

        User adminUser = new User("admin", hashedPassword);
        userFacade.create(adminUser);

        return adminUser;
    }

    private Group createAdminGroup(User adminUser) {
        LOG.info("The 'admin' group did not exist, creating...");

        Group adminGroup = new Group("admin");
        List<User> adminUsers = new ArrayList();
        adminUsers.add(adminUser);

        adminGroup.setUsers(adminUsers);
        groupFacade.create(adminGroup);

        adminUser.setGroup(adminGroup);
        userFacade.edit(adminUser);

        return adminGroup;
    }

    @SuppressWarnings("unchecked")
    private void populateTestData() {
        if (!POPULATE_TEST_DATA) {
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Map<String, Object>> familyDataMap = TestDataHelper.parseFamilyMemberXMLTestData();
        Map<String, FamilyMember> fmEntityByOrigIdMap = new HashMap<>();

        // 1. Create all family members (no bio sections, no family relationships)
        TestDataHelper.filterFamilyMemberOnly(familyDataMap).forEach( (id, fm) -> {
            familyMemberFacade.create(fm);
            fmEntityByOrigIdMap.put(id, fm);
        });

        // 2. Create family relationships
        Map<String, List<FamilyRelationship>> relationships = TestDataHelper.filterFamilyRelationshipsOnly(familyDataMap, fmEntityByOrigIdMap);
        relationships.forEach( (id, relsList) -> {
            relsList.forEach( rel -> familyRelationshipFacade.create(rel) );
        });

        // 3. Update family members with relationships
        List<FamilyMember> updatedFamilyMembers = TestDataHelper.updateFamilyMembersWithRelationships(relationships, fmEntityByOrigIdMap);
        updatedFamilyMembers.forEach( fm -> familyMemberFacade.edit(fm) );

        // 4. Create articles

        // 5. Create links between articles and family members

        // 6. Create links between articles and other articles

        // 7. Create family member bios (raw)
        Map<String, List<FamilyMemberBioSection>> bioSections = TestDataHelper.filterFamilyMemberBioSections(familyDataMap, fmEntityByOrigIdMap);

        bioSections.forEach( (id, list) -> {
            list.forEach( s -> LOG.debug("Family member: {} has section: {}: {}", id, s.getTitle(), s.getContent()));
        });

        bioSections.forEach( (id, sectionsList) -> {
            sectionsList.forEach( bs ->  familyMemberBioSectionFacade.create(bs) );
        });

        // 8. Update family members with bio sections
        updatedFamilyMembers = TestDataHelper.updateFamilyMembersWithBioSections(bioSections, fmEntityByOrigIdMap);
        updatedFamilyMembers.forEach( fm -> familyMemberFacade.edit(fm) );

        // 8. Create family member bio links to family members.

        // 9. Create family member bio links to articles

    }

}

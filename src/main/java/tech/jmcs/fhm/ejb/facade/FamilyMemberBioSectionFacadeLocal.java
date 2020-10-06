package tech.jmcs.fhm.ejb.facade;


import tech.jmcs.fhm.model.FamilyMemberBioSection;

import javax.ejb.Local;
import java.util.List;

@Local
public interface FamilyMemberBioSectionFacadeLocal {
    void create(FamilyMemberBioSection familyMemberBioSection);

    void edit(FamilyMemberBioSection familyMemberBioSection);

    void remove(FamilyMemberBioSection familyMemberBioSection);

    FamilyMemberBioSection find(Object id);

    List<FamilyMemberBioSection> findAll();

    List<FamilyMemberBioSection> findRange(int[] range);

    int count();

}

package tech.jmcs.fhm.ejb.facade;


import java.util.List;
import javax.ejb.Local;

import tech.jmcs.fhm.model.FamilyMember;

/**
 *
 * @author John
 */
@Local
public interface FamilyMemberFacadeLocal {

    void create(FamilyMember familyMember);

    void edit(FamilyMember familyMember);

    void remove(FamilyMember familyMember);

    FamilyMember find(Object id);

    List<FamilyMember> findAll();

    List<FamilyMember> findRange(int[] range);

    int count();

    List<FamilyMember> findByName(String name);

}
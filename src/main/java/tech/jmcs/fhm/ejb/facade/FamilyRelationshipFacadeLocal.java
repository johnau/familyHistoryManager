package tech.jmcs.fhm.ejb.facade;

import tech.jmcs.fhm.model.FamilyRelationship;

import javax.ejb.Local;
import java.util.List;

@Local
public interface FamilyRelationshipFacadeLocal {

    void create(FamilyRelationship familyRelationship);

    void edit(FamilyRelationship familyRelationship);

    void remove(FamilyRelationship familyRelationship);

    FamilyRelationship find(Object id);

    List<FamilyRelationship> findAll();

    List<FamilyRelationship> findRange(int[] range);

    int count();

}

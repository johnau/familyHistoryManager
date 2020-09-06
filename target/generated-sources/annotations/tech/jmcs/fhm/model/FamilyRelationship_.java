package tech.jmcs.fhm.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tech.jmcs.fhm.model.FamilyMember;
import tech.jmcs.fhm.model.RelationshipType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-03T21:00:52")
@StaticMetamodel(FamilyRelationship.class)
public class FamilyRelationship_ { 

    public static volatile SingularAttribute<FamilyRelationship, FamilyMember> a;
    public static volatile SingularAttribute<FamilyRelationship, FamilyMember> b;
    public static volatile SingularAttribute<FamilyRelationship, RelationshipType> relType;
    public static volatile SingularAttribute<FamilyRelationship, Long> id;

}
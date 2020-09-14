package tech.jmcs.fhm.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import tech.jmcs.fhm.model.FamilyRelationship;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-14T15:39:58")
@StaticMetamodel(FamilyMember.class)
public class FamilyMember_ { 

    public static volatile SingularAttribute<FamilyMember, String> firstName;
    public static volatile SingularAttribute<FamilyMember, String> lastName;
    public static volatile ListAttribute<FamilyMember, FamilyRelationship> relationships;
    public static volatile SingularAttribute<FamilyMember, String> otherNames;
    public static volatile SingularAttribute<FamilyMember, Date> dob;
    public static volatile SingularAttribute<FamilyMember, Date> dod;
    public static volatile SingularAttribute<FamilyMember, Long> id;
    public static volatile SingularAttribute<FamilyMember, Long> version;

}
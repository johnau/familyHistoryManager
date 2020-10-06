package tech.jmcs.fhm.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("familyMember_bio")
@Table(name = "family_member_bio_section")
@SequenceGenerator(name = "familyMemberBioSection_sequence", initialValue = 1, allocationSize = 1)
public class FamilyMemberBioSection extends DocumentSection {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    private FamilyMember familyMember;

    public FamilyMemberBioSection() {
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }
}

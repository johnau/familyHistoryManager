/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.jmcs.fhm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author John
 */
@Entity
@Table(name = "family_member")
@SequenceGenerator(name = "familyMember_sequence", initialValue = 100, allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "FamilyMember.countByFirstname", query = "SELECT COUNT(f) FROM FamilyMember f WHERE LOWER(f.firstname) = LOWER(:firstname)"),
    @NamedQuery(name = "FamilyMember.findByFirstname", query = "SELECT f FROM FamilyMember f WHERE LOWER(f.firstname) = LOWER(:firstname)"),
    @NamedQuery(name = "FamilyMember.countByName", query = "SELECT COUNT(f) FROM FamilyMember f WHERE CONCAT(LOWER(f.firstname), ' ', LOWER(f.lastname)) = LOWER(:name)"),
    @NamedQuery(name = "FamilyMember.findByName", query = "SELECT f FROM FamilyMember f WHERE CONCAT(LOWER(f.firstname), ' ', LOWER(f.lastname)) = LOWER(:name)"),
    @NamedQuery(name = "FamilyMember.countByFullName", query = "SELECT COUNT(f) FROM FamilyMember f WHERE LOWER(f.firstname) = LOWER(:firstname) AND LOWER(f.otherNames) = LOWER(:otherNames) AND LOWER(f.lastname) = LOWER(:lastname)"),
    @NamedQuery(name = "FamilyMember.findByFullName", query = "SELECT f FROM FamilyMember f WHERE LOWER(f.firstname) = LOWER(:firstname) AND LOWER(f.otherNames) = LOWER(:otherNames) AND LOWER(f.lastname) = LOWER(:lastname)"),
})
public class FamilyMember implements Serializable {

    private static final long serialVersionUID = 2L;

    @Version
    @Column(name = "optLock", columnDefinition = "integer default 0", nullable = false)
    private Long version = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="familyMember_sequence")
    @Column(name = "id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long id;

    private String firstname;
    
    private String otherNames;
    
    private String lastname;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dod;

    private String birthplace;

    @OneToMany(mappedBy = "a", fetch = FetchType.LAZY)
    private List<FamilyRelationship> relationships;

    private String description;

    @OneToMany(mappedBy = "familyMember", fetch = FetchType.LAZY)
    private List<FamilyMemberBioSection> bioSections;

    public FamilyMember() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDod() {
        return dod;
    }

    public void setDod(Date dod) {
        this.dod = dod;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public List<FamilyRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<FamilyRelationship> relationships) {
        this.relationships = relationships;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FamilyMemberBioSection> getBioSections() {
        return bioSections;
    }

    public void setBioSections(List<FamilyMemberBioSection> bioSections) {
        this.bioSections = bioSections;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FamilyMember)) {
            return false;
        }
        FamilyMember other = (FamilyMember) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tech.jmcs.fhm.model.FamilyMember[ id=" + id + " ]";
    }

}

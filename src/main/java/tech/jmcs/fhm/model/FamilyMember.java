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
@SequenceGenerator(name = "family_member_sequence", initialValue = 1, allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "FamilyMember.countByFirstName", query = "SELECT COUNT(f) FROM FamilyMember f WHERE LOWER(f.firstName) = LOWER(:firstName)"),
    @NamedQuery(name = "FamilyMember.findByFirstName", query = "SELECT f FROM FamilyMember f WHERE LOWER(f.firstName) = LOWER(:firstName)"),
    @NamedQuery(name = "FamilyMember.countByName", query = "SELECT COUNT(f) FROM FamilyMember f WHERE CONCAT(LOWER(f.firstName), ' ', LOWER(f.lastName)) = LOWER(:name)"),
    @NamedQuery(name = "FamilyMember.findByName", query = "SELECT f FROM FamilyMember f WHERE CONCAT(LOWER(f.firstName), ' ', LOWER(f.lastName)) = LOWER(:name)"),
})
public class FamilyMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "optLock", columnDefinition = "integer default 0", nullable = false)
    private Long version = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="family_member_sequence")
    @Column(name = "id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long id;

    private String firstName;
    
    private String otherNames;
    
    private String lastName;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dod;
    
    @OneToMany(mappedBy = "a", fetch = FetchType.LAZY)
    private List<FamilyRelationship> relationships;

    public FamilyMember() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<FamilyRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<FamilyRelationship> relationships) {
        this.relationships = relationships;
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

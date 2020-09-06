/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.jmcs.fhm.model;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

/**
 *
 * @author John
 */
@Entity
@Table(name = "family_relationship")
public class FamilyRelationship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = LAZY) // bidirectional
    private FamilyMember a;
    
    @Enumerated(EnumType.STRING)
    private RelationshipType relType;

    @ManyToOne(fetch = LAZY) // unidirectional
    private FamilyMember b;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FamilyMember getA() {
        return a;
    }

    public void setA(FamilyMember a) {
        this.a = a;
    }

    public RelationshipType getRelType() {
        return relType;
    }

    public void setRelType(RelationshipType relType) {
        this.relType = relType;
    }

    public FamilyMember getB() {
        return b;
    }

    public void setB(FamilyMember b) {
        this.b = b;
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
        if (!(object instanceof FamilyRelationship)) {
            return false;
        }
        FamilyRelationship other = (FamilyRelationship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tech.jmcs.familyhistorymanager.model.FamilyRelationship[ id=" + id + " ]";
    }
    
}

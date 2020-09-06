/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.jmcs.fhm.model;

/**
 *
 * @author John
 */
public enum RelationshipType {
    MOTHER_TO ("MOTHER_TO", "MOTHERED_BY"),
    MOTHERED_BY ("MOTHERED_BY", "MOTHER_TO"),
    FATHER_TO ("FATHER_TO", "FATHERED_BY"),
    FATHERED_BY ("FATHERED_BY", "FATHER_TO"),
    SIBLING ("SIBLING", "SIBLING"),
    MARRIED_TO ("MARRIED_TO", "DIVORCED_FROM"),
    DIVORCED_FROM ("DIVORCED_FROM", "MARRIED_TO");

    private String relName;
    private String inverseRelName;

    private RelationshipType(String relName, String inverseRelName) {
        this.relName = relName;
        this.inverseRelName = inverseRelName;
    }

    @Override
    public String toString() {
        return this.relName;
    }

    public String getNameString() {
        return relName;
    }

    public String getDisplayNameString() { return relName.replace("_", " "); }

    public String getInverseNameString() {
        return inverseRelName;
    }
}

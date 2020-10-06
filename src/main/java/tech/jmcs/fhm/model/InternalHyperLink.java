package tech.jmcs.fhm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "internal_hyper_link")
@SequenceGenerator(name = "internalHyperLink_sequence", initialValue = 1, allocationSize = 1)
public class InternalHyperLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "optLock", columnDefinition = "integer default 0", nullable = false)
    private Long version = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="internalHyperLink_sequence")
    @Column(name = "id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long id;

    private String outcome;

    private Map<String, String> parameters;

    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentSection documentSection;

    public InternalHyperLink() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public DocumentSection getDocumentSection() {
        return documentSection;
    }

    public void setDocumentSection(DocumentSection documentSection) {
        this.documentSection = documentSection;
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
        if (!(object instanceof InternalHyperLink)) {
            return false;
        }
        InternalHyperLink other = (InternalHyperLink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tech.jmcs.fhm.model.InternalHyperLink[ id=" + id + " ]";
    }
}

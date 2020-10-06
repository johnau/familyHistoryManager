package tech.jmcs.fhm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "document_section")
@SequenceGenerator(name="documentSection_sequence", initialValue=100, allocationSize=10)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Document_Type")
public class DocumentSection implements Serializable {

    private static final long serialVersionUID = 2L;

    @Version
    @Column(name = "optLock", columnDefinition = "integer default 0", nullable = false)
    private Long version = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="documentSection_sequence")
    @Column(name = "id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "documentSection", fetch = FetchType.LAZY)
    private List<InternalHyperLink> links;

    private Map<String, String> linkMap;

    public DocumentSection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<InternalHyperLink> getLinks() {
        return links;
    }

    public void setLinks(List<InternalHyperLink> links) {
        this.links = links;
    }

    public Map<String, String> getLinkMap() {
        return linkMap;
    }

    public void setLinkMap(Map<String, String> linkMap) {
        this.linkMap = linkMap;
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
        if (!(object instanceof DocumentSection)) {
            return false;
        }
        DocumentSection other = (DocumentSection) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tech.jmcs.fhm.model.DocumentSection[ id=" + id + " ]";
    }
}

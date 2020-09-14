package tech.jmcs.fhm.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fhm_group")
@SequenceGenerator(name = "group_sequence", initialValue = 1, allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "Group.countByName", query = "SELECT COUNT(g) FROM Group g WHERE g.groupName = LOWER(:name)"),
        @NamedQuery(name = "Group.findByName", query = "SELECT g FROM Group g WHERE g.groupName = LOWER(:name)"),
})
public class Group {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "optLock", columnDefinition = "integer default 0", nullable = false)
    private Long version = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_sequence")
    @Column(name = "id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long id;

    @Column(unique = true) // enforce Unique column on ddl creation
    private String groupName;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<User> users;

    public Group() {
    }

    public Group(String groupName) {
        this.setGroupName(groupName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName.toUpperCase();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
        if (!(object instanceof Group)) {
            return false;
        }
        Group other = (Group) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tech.jmcs.fhm.model.Group[ id=" + id + " ]";
    }
}

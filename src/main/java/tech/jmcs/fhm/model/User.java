package tech.jmcs.fhm.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fhm_user")
@SequenceGenerator(name = "user_sequence", initialValue = 1, allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "User.countByUsername", query = "SELECT COUNT(u) FROM User u WHERE u.username = LOWER(:username)"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = LOWER(:username)"),
})
public class User {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "optLock", columnDefinition = "integer default 0", nullable = false)
    private Long version = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_sequence")
    @Column(name = "id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long id;

    @Column(unique = true) // enforce Unique column on ddl creation
    private String username;

    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    public User() {
    }

    public User(String username, String passwordHash) {
        this.setUsername(username);
        this.setPasswordHash(passwordHash);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tech.jmcs.fhm.model.User[ id=" + id + " ]";
    }
}

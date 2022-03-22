package SYSC6.Project;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Role")
    private RoleType role = RoleType.NO_ROLE;

    public User(){

    }

    public User(String username, String password){
        this(username, password, RoleType.NO_ROLE);
    }

    public User(String username, String password, RoleType role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, Username=%s, Password=%s, Role=%s]",
                id, username, password, role);
    }
}

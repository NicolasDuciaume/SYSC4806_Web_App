package SYSC6.Project.user;
import SYSC6.Project.user.userManagement.UserPOJO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
@Getter
@Setter
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
    @Column(name = "Clicks")
    private int clicks;

    public User(){

    }

    public User(String username, String password){
        this(username, password, RoleType.NO_ROLE);
    }

    public User(String username, String password, RoleType role){
        this.username = username;
        this.password = password;
        this.role = role;
        this.clicks = 0;
    }

    public UserPOJO toPojo(){
        UserPOJO userpojo = new UserPOJO();
        userpojo.setId(this.id);
        userpojo.setUsername(this.username);
        userpojo.setRole(this.role.toString());
        userpojo.setClicks(this.clicks);
        return userpojo;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, Username=%s, Password=%s, Role=%s, Clicks=%d]",
                id, username, password, role, clicks);
    }
}

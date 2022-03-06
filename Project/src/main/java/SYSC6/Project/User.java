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
    private String Username;
    @Column(name = "Password")
    private String Password;

    public User(){

    }

    public User(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, Username=%s, Password=%s]",
                id, Username, Password);
    }
}

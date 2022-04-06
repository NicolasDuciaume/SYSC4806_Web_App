package SYSC6.Project;

import SYSC6.Project.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private Admin admin;
    private User user;
    private String username, password;
    private Long id;

    @BeforeEach
    void setUp(){
        username="admin";
        password="admin";
        id=1234567810L;
        admin = new Admin(username,password,RoleType.ADMIN);
        user = new User();
    }

    @Test
    void checkRole(){
        assertNotEquals(admin.getRole(),user.getRole());
    }






}

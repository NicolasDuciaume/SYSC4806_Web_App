package SYSC6.Project;

import SYSC6.Project.user.RoleType;
import SYSC6.Project.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user1, user2;
    private String username, password;
    private Long id;
    @BeforeEach
    void setUp() {
        username = "student";
        password = "qwerty";
        id = 123456789L;
        user1 = new User();
        user2 = new User(username, password);
        user2.setRole(RoleType.ADMIN);
    }

    @Test
    void getId() {
        assertNull(user1.getId());
        assertNull(user2.getId());

        user1.setId(id);
        assertEquals(id, user1.getId());
    }

    @Test
    void getUsername() {
        assertNull(user1.getUsername());
        assertSame(user2.getUsername(), username);
        assertNotSame("Username12", user2.getUsername());
    }

    @Test
    void getPassword() {
        assertNull(user1.getPassword());
        assertSame(user2.getPassword(), password);
        assertNotSame("pw456", user2.getPassword());
    }

    @Test
    void getRole() {
        assertSame(user1.getRole(), RoleType.NO_ROLE);
        User userDefaultRole = new User("name","pw");
        assertSame(userDefaultRole.getRole(), RoleType.NO_ROLE);
        assertEquals(RoleType.ADMIN,user2.getRole());
    }

    @Test
    void setId() {
        assertNull(user1.getId());
        assertNull(user2.getId());

        user1.setId(id);
        assertEquals(id, user1.getId());

        user2.setId(id);
        assertEquals(id, user2.getId());
        assertNotEquals(4567, (long) user2.getId());
    }

    @Test
    void setUsername() {
        assertNull(user1.getUsername());
        assertSame(user2.getUsername(), username);

        user1.setUsername(username);
        assertSame(user1.getUsername(), username);

        user2.setUsername("User");
        assertEquals("User", user2.getUsername());
    }

    @Test
    void setPassword() {
        assertNull(user1.getPassword());
        assertSame(user2.getPassword(), password);

        user1.setPassword(password);
        assertEquals(password, user1.getPassword());

        user2.setPassword("pw123");
        assertEquals("pw123", user2.getPassword());
    }

    @Test
    void setRole() {
        user2.setRole(RoleType.PAID_USER);
        assertEquals(RoleType.PAID_USER,user2.getRole());
    }
}
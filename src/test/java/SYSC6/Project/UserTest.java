package SYSC6.Project;

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
        id = new Long(123456789);
        user1 = new User();
        user2 = new User(username, password);
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
        assertTrue(user2.getUsername() == username);
        assertFalse(user2.getUsername() == "Username12");
    }

    @Test
    void getPassword() {
        assertNull(user1.getPassword());
        assertTrue(user2.getPassword() == password);
        assertFalse(user2.getPassword() == "pw456");
    }

    @Test
    void setId() {
        assertNull(user1.getId());
        assertNull(user2.getId());

        user1.setId(id);
        assertEquals(id, user1.getId());

        user2.setId(id);
        assertEquals(id, user2.getId());
        assertFalse(user2.getId() == 4567);
    }

    @Test
    void setUsername() {
        assertNull(user1.getUsername());
        assertTrue(user2.getUsername() == username);

        user1.setUsername(username);
        assertTrue(user1.getUsername() == username);

        user2.setUsername("User");
        assertEquals("User", user2.getUsername());
    }

    @Test
    void setPassword() {
        assertNull(user1.getPassword());
        assertTrue(user2.getPassword() == password);

        user1.setPassword(password);
        assertEquals(password, user1.getPassword());

        user2.setPassword("pw123");
        assertEquals("pw123", user2.getPassword());
    }
}
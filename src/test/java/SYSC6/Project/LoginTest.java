package SYSC6.Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    private String u1, u2, p1, p2;
    private Login login;
    @BeforeEach
    void setUp() {
        login = new Login();
        u1 = "bob1";
        p1 = "Password321!";
        u2 = "supermario";
        p2 = "qwerty123";
    }

    @Test
    void getUsername() {
        assertNull(login.getUsername());

        login.setUsername("john");
        assertFalse(login.getUsername() == u1);
        assertFalse(login.getUsername() == u2);

        login.setUsername(u1);
        assertTrue(login.getUsername() == u1);
        assertFalse(login.getUsername() == u2);

        login.setUsername(u2);
        assertTrue(login.getUsername() == u2);
        assertFalse(login.getUsername() == u1);
    }

    @Test
    void setUsername() {
        assertNull(login.getPassword());

        login.setUsername(u1);
        assertTrue(login.getUsername() == u1);
        assertFalse(login.getUsername() == u2);

        login.setUsername(u2);
        assertTrue(login.getUsername() == u2);
        assertFalse(login.getUsername() == u1);
    }

    @Test
    void getPassword() {
        assertNull(login.getPassword());

        login.setPassword("0987654321");
        assertEquals(login.getPassword(), "0987654321");
        assertFalse(login.getPassword() == p1);
        assertFalse(login.getPassword() == p2);

        login.setPassword(p1);
        assertTrue(login.getPassword() == p1);
        assertFalse(login.getPassword() == p2);

        login.setPassword(p2);
        assertTrue(login.getPassword() == p2);
        assertFalse(login.getPassword() == p1);
    }

    @Test
    void setPassword() {
        assertNull(login.getPassword());

        login.setPassword(p1);
        assertTrue(login.getPassword() == p1);
        assertFalse(login.getPassword() == p2);

        login.setPassword(p2);
        assertTrue(login.getPassword() == p2);
        assertFalse(login.getPassword() == p1);
    }
}
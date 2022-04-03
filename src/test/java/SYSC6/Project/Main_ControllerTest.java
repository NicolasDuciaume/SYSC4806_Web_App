package SYSC6.Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class Main_ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login_form"));
    }

    @Test
    void login_process() throws Exception {
        this.mockMvc.perform(post("/login_form").param("id", "1")).andExpect(redirectedUrl("/admin_portal"));
    }

    @Test
    void register() throws Exception {
        this.mockMvc.perform(post("/Register")).andExpect(redirectedUrl("/Registration"));
    }

    @Test
    void reg() throws Exception {
        this.mockMvc.perform(get("/Registration")).andExpect(status().isOk()).andExpect(view().name("Registration"));
    }

    @Test
    void create() throws Exception {
        this.mockMvc.perform(post("/Create").param("user", "Test1").param("pass", "Password")).andExpect(redirectedUrl("/user_portal"));
    }

    @Test
    void logout() {
    }

    @Test
    void greeting() {
    }

    @Test
    void createUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void checkUser() {
    }
}
package SYSC6.Project;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class Main_ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login_form"));
    }

    /*
    @Test
    @Order(3)
    void login_process() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("username", "admin");
        obj.put("password","admin");
        this.mockMvc.perform(post("/rest/api/user/add").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(obj))).andExpect(status().isCreated());
        this.mockMvc.perform(post("/login_form").param("id", "1")).andExpect(redirectedUrl("/admin_portal"));
    }
     */

    @Test
    void register() throws Exception {
        this.mockMvc.perform(post("/Register")).andExpect(redirectedUrl("/Registration"));
    }

    @Test
    void reg() throws Exception {
        this.mockMvc.perform(get("/Registration")).andExpect(status().isOk()).andExpect(view().name("Registration"));
    }

    @Test
    @Order(1)
    void create1() throws Exception {
        this.mockMvc.perform(post("/TempCreate").param("admin", "not").param("id", "1")).andExpect(redirectedUrl("/user_portal"));
    }

    @Test
    @Order(2)
    void create2() throws Exception {
        this.mockMvc.perform(post("/TempCreate").param("admin", "admin").param("id", "2")).andExpect(redirectedUrl("/admin_portal"));
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
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

    /* must stay black as the order of unit test in makes the creation of admin and user account already declared
    @Test
    void login() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login_form"));
    }
    */

<<<<<<< master
=======
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
>>>>>>> Completed user delete method functionality

    @Test
    @Order(3)
    void login_process_admin() throws Exception {
        this.mockMvc.perform(post("/login_form").param("id", "1").param("admin","admin")).andExpect(redirectedUrl("/admin_portal"));
    }

    @Test
    @Order(4)
    void login_process_user() throws Exception {
        this.mockMvc.perform(post("/login_form").param("id", "2").param("admin","not")).andExpect(redirectedUrl("/user_portal"));
    }

<<<<<<< master
=======
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
>>>>>>> Completed user delete method functionality

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
    void logout() throws Exception {
        this.mockMvc.perform(post("/LogOut")).andExpect(redirectedUrl("/"));
    }

}
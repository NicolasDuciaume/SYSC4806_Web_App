package SYSC6.Project;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(2)
    void getUserById() throws Exception {
        this.mockMvc.perform(get("/rest/api/user/"+1)).andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"username\":\"Nick\",\"password\":\"Help\",\"role\":\"FREE_USER\"}"));
    }

    @Test
    @Order(1)
    void getAllUsers() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("username", "Nick");
        obj.put("password","Help");
        this.mockMvc.perform(post("/rest/api/user/add").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(obj)));
        obj.put("username", "Nazifa");
        obj.put("password","Friend");
        this.mockMvc.perform(post("/rest/api/user/add").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(obj)));
        this.mockMvc.perform(get("/rest/api/user")).andExpect(status().isOk()).andExpect(content().json("[{\"id\":1,\"username\":\"Nick\",\"password\":\"Help\",\"role\":\"FREE_USER\"},{\"id\":2,\"username\":\"Nazifa\",\"password\":\"Friend\",\"role\":\"FREE_USER\"}]"));
    }

    @Test
    @Order(3)
    void getUserByUsernameExists() throws Exception{
        this.mockMvc.perform(get("/rest/api/user/get/Nick")).andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void getUserByUsernameNotExists() throws Exception{
        this.mockMvc.perform(get("/rest/api/user/get/test")).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void getUserByUsernameandPassword_WrongPass() throws  Exception{
        this.mockMvc.perform(get("/rest/api/user/get/Nick/Wrong")).andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    void getUserByUsernameandPassword_NotExist() throws  Exception{
        this.mockMvc.perform(get("/rest/api/user/get/test/Wrong")).andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    void getUserByUsernameandPassword_GoodCred() throws  Exception{
        JSONObject obj = new JSONObject();
        obj.put("username", "Test");
        obj.put("password","Check");
        this.mockMvc.perform(post("/rest/api/user/add").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(obj)));
        this.mockMvc.perform(get("/rest/api/user/get/Test/Check")).andExpect(status().isOk()).andExpect(content().json("{\"id\":3,\"username\":\"Test\",\"password\":\"Check\",\"role\":\"FREE_USER\"}"));
    }


    @Test
    @Order(8)
    void createUser() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("username", "Nick");
        obj.put("password","Help");
        this.mockMvc.perform(post("/rest/api/user/add").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(obj))).andExpect(status().isCreated()).andExpect(content().json("{\"id\":4,\"username\":\"Nick\",\"password\":\"Help\",\"role\":\"FREE_USER\"}"));
    }
}
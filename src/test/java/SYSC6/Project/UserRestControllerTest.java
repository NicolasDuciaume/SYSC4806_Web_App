package SYSC6.Project;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void createUser() throws Exception {
        JSONObject obj = new JSONObject();
        this.mockMvc.perform(post("/rest/api/usertable").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(obj))).andExpect(status().isCreated()).andExpect(content().json("{\"id\":4,\"username\":\"Nick\",\"role\":\"FREE_USER\"}"));
    }

}
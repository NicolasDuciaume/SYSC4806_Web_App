package SYSC6.Project;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserPortalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getId0() throws Exception {
        this.mockMvc.perform(get("/user/0").param("id", "1")).andExpect(redirectedUrl("/user_portal"));
    }

    @Test
    @Order(3)
    void getId1() throws Exception {
        this.mockMvc.perform(get("/user/1").param("id", "1")).andExpect(redirectedUrl("/user_portal"));
    }

    @Test
    @Order(2)
    void greeting_invalid_id() throws Exception {
        this.mockMvc.perform(get("/user_portal")).andExpect(status().isOk()).andExpect(view().name("login_form"));
    }

    @Test
    @Order(4)
    void greeting_valid_id() throws Exception {
        this.mockMvc.perform(get("/user_portal")).andExpect(status().isOk()).andExpect(view().name("user_portal"));
    }

    @Test
    @Order(6)
    void goBack() throws Exception {
        this.mockMvc.perform(post("/GoBack")).andExpect(redirectedUrl("/user_portal"));
    }

    @Test
    @Order(5)
    void enterApp() throws Exception {
        this.mockMvc.perform(post("/Click")).andExpect(redirectedUrl("/app_proxy"));
    }
}
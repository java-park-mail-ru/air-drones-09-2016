package ru.mail.park.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.util.derby.sys.Sys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.controllers.api.PutUserRequest;
import ru.mail.park.controllers.api.SignInRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by admin on 27.10.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registration() throws Exception {

        SignInRequest body = new SignInRequest("test@mail.ru", "12345678qQ");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(body);

        ResultActions auth = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        MvcResult result = auth.andReturn();

        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.email").value("test@mail.ru"))
                .andExpect(jsonPath("$.response.username").value("Anonymus"))
                .andExpect(jsonPath("$.response.rating").value(0));

    }

    @Test
    public void deleteUser() throws Exception {

        SignInRequest body = new SignInRequest("test@mail.ru", "12345678qQ");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(body);

        ResultActions auth = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        MvcResult result = auth.andReturn();

        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();

        mockMvc.perform(MockMvcRequestBuilders.delete("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser() throws Exception {

        SignInRequest body = new SignInRequest("test@mail.ru", "12345678qQ");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(body);

        ResultActions auth = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        MvcResult result = auth.andReturn();

        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();

        PutUserRequest putUserRequest = new PutUserRequest("test@mail.ru", "12345678qQ",
                                                                            "NEWUSER", null);
        json = mapper.writeValueAsString(putUserRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.email").value("test@mail.ru"))
                .andExpect(jsonPath("$.response.username").value("NEWUSER"))
                .andExpect(jsonPath("$.response.rating").value(0));
    }


}
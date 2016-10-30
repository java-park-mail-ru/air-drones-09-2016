package ru.mail.park.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.controllers.api.SignInRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by admin on 27.10.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sessionTest() throws Exception {

        SignInRequest body = new SignInRequest("test@mail.ru", "12345678qQ");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(body);

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        ).andExpect(status().isBadRequest());

        ResultActions auth = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
        ).andExpect(status().isOk());

        MvcResult result = auth.andReturn();

        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();

        mockMvc.perform(delete("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session)
        ).andExpect(status().isOk());

        mockMvc.perform(delete("/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).session(session)
        ).andExpect(status().isUnauthorized());

    }

}
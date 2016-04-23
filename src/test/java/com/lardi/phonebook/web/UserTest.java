package com.lardi.phonebook.web;

import com.google.gson.Gson;
import com.lardi.phonebook.common.GsonHolder;
import com.lardi.phonebook.config.ApplicationConfig;
import com.lardi.phonebook.config.TestDBConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 * @author Nikolay Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class, TestDBConfig.class})
public class UserTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private Gson gson = GsonHolder.gson;

    @Before
    public void before() {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void login() throws Exception {

        mockMvc.perform(post("/auth")
                .content(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("login", "vkont4@gmail.com").param("password", "111111112"))
                .andExpect(jsonPath("$.success", equalTo(false)));

        mockMvc.perform(post("/auth")
                .content(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("login", "vkont4@gmail.com").param("password", "11111111"))
                .andExpect(jsonPath("$.success", equalTo(true)));
    }
}

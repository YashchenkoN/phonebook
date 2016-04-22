package com.lardi.phonebook.web;

import com.google.gson.Gson;
import com.lardi.phonebook.common.GsonHolder;
import com.lardi.phonebook.config.ApplicationConfig;
import com.lardi.phonebook.config.TestDBConfig;
import com.lardi.phonebook.dto.PhoneBookRecordDTO;
import com.lardi.phonebook.service.PhoneBookRecordService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Nikolay Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class, TestDBConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBookRecordEndpointTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PhoneBookRecordService phoneBookRecordService;

    private MockMvc mockMvc;
    private Gson gson = GsonHolder.gson;

    @Before
    public void before() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "read_by_login")
    public void readHttp() throws Exception {
        mockMvc.perform(get("/api/record/"))
                .andExpect(jsonPath("$", hasSize(4)));

        assertThat(phoneBookRecordService.getByUserId(1L).size(), equalTo(4));

        mockMvc.perform(get("/api/record/?firstname=Name"))
                .andExpect(jsonPath("$", hasSize(1)));

        assertThat(phoneBookRecordService.getByFirstName("Name", 1L).size(), equalTo(1));

        mockMvc.perform(get("/api/record/?lastname=gettest"))
                .andExpect(jsonPath("$", hasSize(1)));

        assertThat(phoneBookRecordService.getByLastName("gettest", 1L).size(), equalTo(1));
    }

    @Test
    @WithMockUser(username = "vkont4@gmail.com")
    public void createHttp() throws Exception {
        PhoneBookRecordDTO phoneBookRecordDTO = new PhoneBookRecordDTO();
        phoneBookRecordDTO.setFirstName("CREATE");
        phoneBookRecordDTO.setLastName("CREATE");
        phoneBookRecordDTO.setPatronymic("CREATE");
        phoneBookRecordDTO.setUserId(3L);

        mockMvc.perform(post("/api/record/").content(gson.toJson(phoneBookRecordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)));

        phoneBookRecordDTO.setMobilePhone("+380(99)9994343");

        mockMvc.perform(post("/api/record/").content(gson.toJson(phoneBookRecordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @WithMockUser(username = "vkont4@gmail.com")
    public void updateHttp() throws Exception {
        PhoneBookRecordDTO phoneBookRecordDTO = new PhoneBookRecordDTO();
        phoneBookRecordDTO.setFirstName("UPDATE");
        phoneBookRecordDTO.setLastName("UPDATE");
        phoneBookRecordDTO.setPatronymic("UPDATE");
        phoneBookRecordDTO.setUserId(3L);

        mockMvc.perform(put("/api/record/" + 6).content(gson.toJson(phoneBookRecordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)));

        phoneBookRecordDTO.setMobilePhone("+380(99)9994344");

        mockMvc.perform(put("/api/record/" + 6).content(gson.toJson(phoneBookRecordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @WithMockUser(username = "delete_record")
    public void deleteHttp() throws Exception {
        mockMvc.perform(delete("/api/record/" + 7))
                .andExpect(status().isOk());
    }
}

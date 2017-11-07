/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer;

import de.qaware.edu.cc.benutzer.rest.controller.v0.BenutzerController;
import de.qaware.edu.cc.benutzer.service.AppService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test the benutzer controller.
 *
 * Created by simon.baeumler on 14.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BenutzerTestConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class BenutzerControllerTest {

    private MockMvc mvc;

    @Autowired
    AppService myService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new BenutzerController(myService)).build();
    }

    @Test
    public void testGetBenutzerData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(de.qaware.edu.cc.benutzer.BenutzerService.VALUE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .param("name", "testval"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid", is(1)))
                .andExpect(jsonPath("$.content", is("testval")))
                .andExpect(jsonPath("$.someValue", is("http://test.url")));
    }

    @Test
    public void testGetBenutzerNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/benutzernotfound")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}

package com.sadatmalik.fusionbanking.controllers;

import com.sadatmalik.fusionbanking.oauth.hsbc.HsbcAuthenticationEndpoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HsbcControllerIT {
    @Autowired
    HsbcController controller;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testWebOnlyContextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void testGetAuthorizationUrl() throws Exception {
        mockMvc.perform(get("/get-auth-url"))

                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(content().string(containsString("redirect:" +
                        HsbcAuthenticationEndpoints.AUTHORIZE_URL)));
    }

}
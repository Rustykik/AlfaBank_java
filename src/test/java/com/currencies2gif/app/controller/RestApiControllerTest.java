package com.currencies2gif.app.controller;

import com.currencies2gif.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RestApiController underTest;

    @MockBean
    UserService service;

    @Test
    void getYesterdayRates() {
    }

    @Test
    void getLatestRates() {
    }

    @Test
    void getGif() {
    }
}
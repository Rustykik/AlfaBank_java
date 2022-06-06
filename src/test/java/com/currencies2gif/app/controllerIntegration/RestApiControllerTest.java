package com.currencies2gif.app.controllerIntegration;

import com.currencies2gif.app.controller.RestApiController;
import com.currencies2gif.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RestApiController underTest;

    @Autowired
    UserService service;

    @Test
    void getYesterdayRates_ShouldWorkCorrectly() throws Exception {
        //given
        String cur = "EUR";
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/yesterday", cur))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    void getYesterday_ShouldReturnStatus400() throws Exception {
        //given
        String cur = "EUdfsaR";
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/yesterday", cur))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void getLatestRates_ShouldWorkCorrectly() throws Exception {
        //given
        String cur = "EUR";
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/latest", cur))
                .andExpect(status().isOk());
    }

    @Test
    void getLatestRates_ShouldReturnStatus400() throws Exception {
        //given
        String cur = "EUdfsaR";
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/latest", cur))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    void getGif_ShouldWorkCorrectly() throws Exception {
        //given
        String tag = "rich";
        ///when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gifs/random").param("tag", tag))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.url").exists());
    }
}
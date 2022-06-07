package com.currencies2gif.app.controllerUnitTest;

import com.currencies2gif.app.controller.RestApiController;
import com.currencies2gif.app.exception.ApiRequestCurrencyException;
import com.currencies2gif.app.exception.ThirdPartyApiInvalidAnswerException;
import com.currencies2gif.app.model.Gif;
import com.currencies2gif.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest//(classes = RestApiControllerTestConfig.class)
@AutoConfigureMockMvc
class RestApiControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RestApiController underTest;

    @MockBean
    UserService service;

    @Test
    void getYesterdayRates_ShouldWorkCorrectly() throws Exception {
        //given
        String cur = "EUR";
        Mockito.when(service.getYesterdayRates(cur)).thenReturn(BigDecimal.TEN);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/yesterday", cur))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    void getYesterday_ShouldReturnStatus400() throws Exception {
        //given
        String cur = "EUdfsaR";
        Mockito.when(service.getYesterdayRates(cur)).thenThrow(ApiRequestCurrencyException.class);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/yesterday", cur))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void getYesterday_ShouldReturnStatus500() throws Exception {
        //given
        String cur = "EUdfsaR";
        Mockito.when(service.getYesterdayRates(cur)).thenThrow(ThirdPartyApiInvalidAnswerException.class);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/yesterday", cur))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void getLatestRates_ShouldWorkCorrectly() throws Exception {
        //given
        String cur = "EUR";
        Mockito.when(service.getLatestRates(cur)).thenReturn(BigDecimal.TEN);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/latest", cur))
                .andExpect(status().isOk());
    }

    @Test
    void getLatestRates_ShouldReturnStatus400() throws Exception {
        //given
        String cur = "EUdfsaR";
        Mockito.when(service.getLatestRates(cur)).thenThrow(ApiRequestCurrencyException.class);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/latest", cur))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void getLatestRates_ShouldReturnStatus500() throws Exception {
        //given
        String cur = "EUdfsaR";
        Mockito.when(service.getLatestRates(cur)).thenThrow(ThirdPartyApiInvalidAnswerException.class);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/{CUR}/latest", cur))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void getGif_ShouldWorkCorrectly() throws Exception {
        //given
        String tag = "rich";
        Mockito.when(service.getGif(tag)).thenReturn(new Gif("httpthatmock : )"));
        ///when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gifs/random").param("tag", tag))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.url").exists());
    }

    @Test
    void getGif_ShouldReturn500() throws Exception {
        //given
        String tag = "rich";
        Mockito.when(service.getGif(tag)).thenThrow(ThirdPartyApiInvalidAnswerException.class);
        ///when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gifs/random").param("tag", tag))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
}
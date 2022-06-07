package com.currencies2gif.app.service;

import com.currencies2gif.app.exception.ApiRequestCurrencyException;
import com.currencies2gif.app.exception.ThirdPartyApiInvalidAnswerException;
import com.currencies2gif.app.feignclients.FeignCurrencyClient;
import com.currencies2gif.app.feignclients.FeignGifClient;
import com.currencies2gif.app.model.Gif;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService underTest;

    @MockBean
    FeignCurrencyClient mockFeignCurrencyClient;

    @MockBean
    FeignGifClient mockFeignGifClient;

    @Test
    void getYesterdayRates_Should_Get_Rates() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, symbol))
                .thenReturn(ServiceTestSetup.CURRENCY_VALID_200);
        //when
        BigDecimal currency = underTest.getYesterdayRates(symbol);
        //then
        assertThat(currency).isNotNull();

    }

    @Test
    void getYesterdayRates_Get_InvalidRequest_And_Should_ReturnApiRequestCurrencyException() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, symbol))
                .thenReturn(ServiceTestSetup.INVALID_CURRENCY_REQUEST_200);
        //when

        //then
        assertThatThrownBy(()->underTest.getYesterdayRates(symbol)).isInstanceOf(ApiRequestCurrencyException.class);

    }

    @Test
    void getYesterdayRates_Get_401_And_Should_ReturnThirdPartyApiInvalidAnswerExcept() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, symbol))
                .thenThrow(FeignException.Forbidden.class);
        //when

        //then
        assertThatThrownBy(()->underTest.getYesterdayRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);

    }

    @Test
    void getYesterdayRates_Get_403_And_Should_ReturnThirdPartyApiInvalidAnswerExcept() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, symbol))
                .thenThrow(FeignException.Unauthorized.class);
        //when

        //then
        assertThatThrownBy(()->underTest.getYesterdayRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);
    }

    @Test
    void getYesterdayRates_Should_Throw_ThirdPartyApiInvalidAnswerException() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, symbol)).thenThrow(FeignException.class);
        //when

        //then
        assertThatThrownBy(() -> underTest.getYesterdayRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);
    }

    @Test
    void getLatest_Should_Get_Rates() {
        //given
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies(symbol)).thenReturn(ServiceTestSetup.CURRENCY_VALID_200);
        //when
        BigDecimal currency = underTest.getLatestRates(symbol);
        //then
        assertThat(currency).isNotNull();
    }

    @Test
    void getLatestRates_Get_InvalidRequest_And_Should_ReturnApiRequestCurrencyException() {
        //given
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies( symbol))
                .thenReturn(ServiceTestSetup.INVALID_CURRENCY_REQUEST_200);
        //when

        //then
        assertThatThrownBy(()->underTest.getLatestRates(symbol)).isInstanceOf(ApiRequestCurrencyException.class);

    }

    @Test
    void getLatestRates_Get_401_And_Should_ReturnThirdPartyApiInvalidAnswerExcept() {
        //given
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies(symbol))
                .thenThrow(FeignException.Forbidden.class);
        //when

        //then
        assertThatThrownBy(()->underTest.getLatestRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);

    }

    @Test
    void getLatestRates_Get_403_And_Should_ReturnThirdPartyApiInvalidAnswerExcept() {
        //given
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies(symbol))
                .thenThrow(FeignException.Unauthorized.class);
        //when

        //then
        assertThatThrownBy(()->underTest.getLatestRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);
    }

    @Test
    void getGif_ShouldWorkCorrect() {
        //given
        String tag = "rich";
        Mockito.when(mockFeignGifClient.getRandomGif(tag)).thenReturn(ServiceTestSetup.GIF_FOUND_200);

        //when
        Gif toCheck = underTest.getGif(tag);

        //then
        assertThat(toCheck.getUrl()).isNotNull();
    }

    @Test
    void getGif_ShouldWorkCorrect2() {
        //given
        String tag = "rich";
        Mockito.when(mockFeignGifClient.getRandomGif(tag)).thenReturn(ServiceTestSetup.GIF_FOUND1_200);

        //when
        Gif toCheck = underTest.getGif(tag);

        //then
        assertThat(toCheck.getUrl()).isNotNull();
    }

    @Test
    void getGif_ShouldThrowException() {
        //given
        String tag = "rich";
        Mockito.when(mockFeignGifClient.getRandomGif(tag)).thenReturn(ServiceTestSetup.GIF_NOT_FOUND);

        //when

        //then
        assertThatThrownBy(() -> underTest.getGif(tag)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);
    }

}
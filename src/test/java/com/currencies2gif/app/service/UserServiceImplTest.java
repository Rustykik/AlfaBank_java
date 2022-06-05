package com.currencies2gif.app.service;

import com.currencies2gif.app.exception.ApiRequestCurrencyException;
import com.currencies2gif.app.exception.ThirdPartyApiInvalidAnswerException;
import com.currencies2gif.app.feignclients.FeignCurrencyClient;
import com.currencies2gif.app.feignclients.FeignGifClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {


    /*
    {
  "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
  "license": "https://openexchangerates.org/license",
  "timestamp": 1654451788,
  "base": "USD",
  "rates": {}
}
{
  "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
  "license": "https://openexchangerates.org/license",
  "timestamp": 1654451788,
  "base": "USD",
  "rates": {
    "BTC": 0.000033287503
  }
}
{
  "error": true,
  "status": 403,
  "message": "not_allowed",
  "description": "Changing the API `base` currency is available for Developer, Enterprise and Unlimited plan clients. Please upgrade, or contact support@openexchangerates.org with any questions."
}
{
  "error": true,
  "status": 401,
  "message": "invalid_app_id",
  "description": "Invalid App ID provided. Please sign up at https://openexchangerates.org/signup, or contact support@openexchangerates.org."
}
https://openexchangerates.org/api/latest.json?base=RUB&app_id=3009f7558066428f83561e07c8c8309c&symbols=BT
     */
    String CORRECT_CURRENCY_JSON = "{\n" +
            "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
            "  \"license\": \"https://openexchangerates.org/license\",\n" +
            "  \"timestamp\": 1654451788,\n" +
            "  \"base\": \"USD\",\n" +
            "  \"rates\": {\n" +
            "    \"BTC\": 0.000033287503\n" +
            "  }\n" +
            "}";
    String WRONG_CURRENCY_JSON = "{\n" +
            "  \"error\": true,\n" +
            "  \"status\": 401,\n" +
            "  \"message\": \"invalid_app_id\",\n" +
            "  \"description\": \"Invalid App ID provided. Please sign up at https://openexchangerates.org/signup, or contact support@openexchangerates.org.\"\n" +
            "}";

    String CORRECT_GIF_JSON = "cor";
    String WRONG_GIF_JSON = "wrg";

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
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, "BTC")).thenReturn(CORRECT_CURRENCY_JSON);
        //when
        BigDecimal currency = underTest.getYesterdayRates(symbol);
        //then
        assertThat(currency).isNotNull();

    }

    @Test
    void getYesterdayRates_Should_Throw_ApiRequestCurrencyException() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, "BTC")).thenThrow(FeignException.class);
        //when

        //then
        assertThatThrownBy(() -> underTest.getYesterdayRates(symbol)).isInstanceOf(ApiRequestCurrencyException.class);
    }

    @Test
    void getYesterdayRates_Should_Throw_ThirdPartyApiInvalidAnswerException() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        String symbol = "USD";
        Mockito.when(mockFeignCurrencyClient.getCurrenciesOnDate(currTime, "USD")).thenThrow(FeignException.class);
        //when

        //then
        assertThatThrownBy(() -> underTest.getYesterdayRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);
    }

    @Test
    void getLatest_Should_Get_Rates() {
        //given
        String symbol = "BTC";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies("BTC")).thenReturn(CORRECT_CURRENCY_JSON);
        //when
        BigDecimal currency = underTest.getLatestRates(symbol);
        //then
        assertThat(currency).isNotNull();
    }

    @Test
    void getLatest_Should_Throw_ApiRequestCurrencyException() {
        //given
        String symbol = "USD";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies("USD")).thenThrow(FeignException.class);
        //when

        //then
        assertThatThrownBy(() -> underTest.getYesterdayRates(symbol)).isInstanceOf(ApiRequestCurrencyException.class);
    }

    @Test
    void getLatest_Should_Throw_ThirdPartyApiInvalidAnswerException() {
        //given
        String symbol = "USD";
        Mockito.when(mockFeignCurrencyClient.getLatestCurrencies("USD")).thenThrow(FeignException.class);
        //when

        //then
        assertThatThrownBy(() -> underTest.getYesterdayRates(symbol)).isInstanceOf(ThirdPartyApiInvalidAnswerException.class);
    }

    @Test
    void getGif() {
    }
}
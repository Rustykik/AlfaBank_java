package com.currencies2gif.app.service;

import com.currencies2gif.app.exception.ApiRequestCurrencyException;
import com.currencies2gif.app.exception.ThirdPartyApiInvalidAnswerException;
import com.currencies2gif.app.feignclients.FeignCurrencyClient;
import com.currencies2gif.app.feignclients.FeignGifClient;
import com.currencies2gif.app.model.Gif;
import com.currencies2gif.app.utils.GifParser;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final FeignCurrencyClient currencyClient;
    private final FeignGifClient gifClient;
    /**
     *
     */
    public BigDecimal getYesterdayRates(String symbol) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currTime = LocalDate.now().minusDays(1).format(formatter);
        BigDecimal currency;
        try {
            JSONObject obj = new JSONObject(currencyClient.getCurrenciesOnDate(currTime, symbol));
            currency = obj.getJSONObject("rates").getBigDecimal(symbol);
        } catch (FeignException e) {
            log.warn("wrong request", e);
            throw new ApiRequestCurrencyException("Api requests per month reached limit or you entered not valid currency request, please check that " + symbol + " is in list of available currencies");
        } catch (JSONException e) {
            log.warn("Third party api returns invalid JSON", e);
            throw new ThirdPartyApiInvalidAnswerException("Sorry third party api is not working");
        }

        return currency;
    }

    public BigDecimal getLatestRates(String symbol) {
        BigDecimal currency;
        try {
            JSONObject obj = new JSONObject(currencyClient.getLatestCurrencies(symbol));
            currency = obj.getJSONObject("rates").getBigDecimal(symbol);
        } catch (FeignException e) {
            log.warn("wrong request", e);
            throw new ApiRequestCurrencyException("Api requests per month reached limit or you entered not valid currency request, please check that " + symbol + " is in list of available currencies");
        } catch (JSONException e) {
            log.warn("Third party api returns invalid JSON", e);
            throw new ThirdPartyApiInvalidAnswerException("Sorry third party api is not working");
        }

        return currency;
    }

    // ToDo make rich broke 2 variables and split it on 2 methods in feign
    public Gif getGif(String tag) {
        String id;
        try {
            String ret = gifClient.getRandomGif(tag);
            String url = GifParser.getUrlFromRawJSON(ret);
            id = GifParser.parseIdFromUrl(url);
        } catch (JSONException | FeignException e) {
            log.warn("Third party api returns invalid JSON", e);
            throw new ThirdPartyApiInvalidAnswerException("Sorry third party api is not working");
        }

        return new Gif("https://i.giphy.com/" + id + ".gif");
    }

}

package com.currencies2gif.app.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${feign.clients.currency.name}", url = "${feign.clients.currency.url}")
public interface FeignCurrencyClient {
    /**
     * TODO maybe return something else instead of string
     * @param date
     * @param symbols
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "historical/{date}.json?base=${feign.clients.currency.base}&app_id=${feign.clients.currency.app_id}")
    public String getCurrenciesOnDate(@PathVariable String date, @RequestParam String symbols);

    @RequestMapping(method = RequestMethod.GET, value = "/latest.json?base=${feign.clients.currency.base}&app_id=${feign.clients.currency.app_id}")
    public String getLatestCurrencies(@RequestParam String symbols);


}

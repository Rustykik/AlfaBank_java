package com.currencies2gif.app.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${feign.clients.gif.name}", url = "${feign.clients.gif.url}")
public interface FeignGifClient {

    @RequestMapping(method = RequestMethod.GET, value = "gifs/random?api_key=${feign.clients.gif.api_key}")
    public String getRandomGif(@RequestParam String tag);

}

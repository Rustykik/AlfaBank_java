package com.currencies2gif.app.controller;

import com.currencies2gif.app.model.Gif;
import com.currencies2gif.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class RestApiController {

    private final UserService service;

    @CrossOrigin
    @GetMapping("currencies/{cur}/yesterday")
    public BigDecimal getYesterdayRates(@PathVariable String cur) {
        return service.getYesterdayRates(cur);
    }

    @CrossOrigin
    @GetMapping("currencies/{cur}/latest")
    public BigDecimal getLatestRates(@PathVariable String cur) {
        return service.getLatestRates(cur);
    }

    @CrossOrigin
    @GetMapping("gifs/random")
    public Gif getGif(@RequestParam String tag) {
        return service.getGif(tag);
    }
}

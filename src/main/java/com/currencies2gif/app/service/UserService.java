package com.currencies2gif.app.service;

import com.currencies2gif.app.model.Gif;

import java.math.BigDecimal;

public interface UserService {

    BigDecimal getYesterdayRates(String cur);
    BigDecimal getLatestRates(String cur);
    Gif getGif(String tag);

}

package com.currencies2gif.app.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GifParserTest {

    @Test
    void getUrlFromRawJSON() {
        //given
    }

    @Test
    void parseIdFromUrlShouldParseCorrectUrl() {
        // given
        String url = "https://media0.giphy.com/media/XEaPFbF62JCk9UWrhz/giphy.gif?cid=d661a53512af432229e4b697fefdbf16fd8f5d245b187807&rid=giphy.gif&ct=g";
        //when
        String id = GifParser.parseIdFromUrl(url);
        // then
        assertThat(id).isEqualTo("XEaPFbF62JCk9UWrhz");
    }
}
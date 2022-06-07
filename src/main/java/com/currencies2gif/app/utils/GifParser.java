package com.currencies2gif.app.utils;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GifParser {

    public static String getUrlFromRawJSON(String json) {
        JSONObject obj = new JSONObject(json);
        String gifUrl = obj.getJSONObject("data")
                .getJSONObject("images")
                .getJSONObject("original")
                .getString("url");
        return gifUrl;
    }

    public static String parseIdFromUrl(String url) {
        final String regex = "(?<=/media/)(.*)(?=/giphy)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        return matcher.group(0);
    }
}

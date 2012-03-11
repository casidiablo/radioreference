package com.radioreference.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

class Utils {
    private static final String TAG = "radioreference:Utils";

    /**
     * Joins a Map to URL string like: baseUrl?key1=value1&key2=value2...
     *
     * @param baseUrl    Optional base url to append to
     * @param parameters Map with keys and values
     *                   Note: Null values are omitted with its keys
     * @param utf8Encode if true uses UTF-8 to encode values
     * @return String
     *         - baseUrl if there is no parameters (can be null)
     *         - String starting with ? if there is no baseUrl i.e: ?key1=value1&key2=value2...
     *         - baseUrl plus parameters i.e: baseUrl?key1=value1&key2=value2...
     */
    public static String urlEncode(String baseUrl, Map<String, String> parameters, boolean utf8Encode) {
        if (parameters != null && parameters.size() > 0) {
            StringBuilder paramBuilder = new StringBuilder();

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null && !baseUrl.contains(key + "=")) {
                    //Only parameters with values and non existent
                    if (utf8Encode) {
                        try {
                            value = URLEncoder.encode(value, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            System.out.printf("E/%s %s\n", TAG, "Unable to encode param to UTF-8");
                        }//If UTF-8 is not supported does nothing
                    }
                    paramBuilder.append("&").append(key)
                            .append("=").append(value);
                }
            }

            int paramStart = baseUrl.indexOf('?');

            if (paramStart == -1) {//If doesn't has a ? add it
                paramBuilder.setCharAt(0, '?');//Replaces the first & with ?
            }

            baseUrl += paramBuilder.toString();
        }
        return baseUrl;
    }
}

package com.radioreference.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class Utils {
    /**
     * A series of String methods
     *
     * @version 1.0
     */
    static class StringUtils {
        private static final String TAG = "Twitvid.StringUtils";

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
                baseUrl = baseUrl == null ? "?" : baseUrl;//If it is null use empty to prevent NPE

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
                                Logger.e(TAG, "Unable to encode param to UTF-8", e);
                            }//If UTF-8 is not supported does nothing
                        }
                        paramBuilder.append("&").append(key)
                                .append("=").append(value);
                    }
                }

                int paramStart = baseUrl.indexOf('?');

                if (paramStart == -1) {//If doesn't has a ? add it
                    paramBuilder.setCharAt(0, '?');//Replaces the first & with ?
                } else if (baseUrl.endsWith("?") || baseUrl.endsWith("&")) {
                    paramBuilder.deleteCharAt(0);//Removes the first &
                }

                baseUrl += paramBuilder.toString();
            }
            return baseUrl;
        }

        /**
         * Encodes a value to UTF-8 URL encoding to sent
         *
         * @param value The value to encode
         * @return Encoded representation of the value
         *         or value itself if unable to encode or invalid
         */
        public static String urlEncodeUtf8(String value) {
            if (value != null && value.length() > 0) {
                try {
                    return URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Logger.e(TAG, "Unable to encode url to UTF-8", e);
                } //If UTF-8 is not supported does nothing
            }
            return value;
        }

        /**
         * Returns the empty String "" if only if string is null
         *
         * @param string String to evaluate
         * @return The empty string "" if only if string is null
         *         string itself otherwise
         */
        public static String emptyIfNull(String string) {
            if (string == null) {
                return "";
            }
            return string;
        }

    }

    /**
     *
     * @author cristian
     */
    static class Logger {
        public static void d(String tag, String message) {
            if (isDebugEnabled()) {
                System.out.printf("D/%s %s\n", tag, message);
            }
        }

        private static boolean isDebugEnabled() {
            return "true".equals(System.getProperty("com.twitvid.api.debug"));
        }

        public static void e(String tag, String message, Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            System.out.printf("E/%s %s\n", tag, message);
        }

        public static void e(String tag, String message) {
            System.out.printf("E/%s %s\n", tag, message);
        }
    }

    /**
     * Set of methods useful to compare objects
     *
     * @version 1.0
     */
    static class CompareUtils {
        /**
         * Compares with equals two objects if first is not null,
         * if first is null compares it with ==
         *
         * @param obj1 first object to compare
         * @param obj2 second object to compare
         * @return true if the objects are equals false otherwise
         */
        public static boolean areEquals(Object obj1, Object obj2) {
            if (obj1 != null) {
                return obj1.equals(obj2);
            }
            return obj1 == obj2;
        }

        /**
         * Compare two maps by its contents
         * It is an alternative to Map.equals function, it pretends to compare two maps.
         * by comparing size, keys and values rater than if they are the same object.
         * Note: In case one of the maps is not instance of map == operator will be used.
         *
         * @param map1 first map to compare
         * @param map2 second map to compare
         * @return true if both has the same size, keys and values
         *         true in case one of them is null and when compared with == returns true (i.e. null == null)).
         *         false otherwise
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        public static boolean areMapsContentEquals(Map map1, Map map2) {
            if (map1 != null && map2 != null) {
                if (!(map1.size() == map2.size() && areEquals(map1.keySet(), map2.keySet()))) {//If aren't same size and same keys
                    return false;
                }

                List values1 = new ArrayList(map1.values());
                List values2 = new ArrayList(map2.values());

                if (map1.size() > 1) {//If there are more than one element must be sorted
                    Collections.sort(values1);
                    Collections.sort(values2);
                }

                return areEquals(values1, values2);
            }
            return map1 == map2;
        }
    }
}

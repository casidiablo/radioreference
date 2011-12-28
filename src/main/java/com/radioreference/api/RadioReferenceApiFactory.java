package com.radioreference.api;

import java.util.HashMap;
import java.util.Map;

public class RadioReferenceApiFactory {

    private static Map<Long, RadioReferenceApi> apis = new HashMap<Long, RadioReferenceApi>();
    private static Map<Long, RadioReferenceApi> debugApis = new HashMap<Long, RadioReferenceApi>();

    public static RadioReferenceApi getApi(long key) {
        if (!apis.containsKey(key)) {
            apis.put(key, new RadioReferenceApiImpl(key, false));
        }
        return apis.get(key);
    }

    public static RadioReferenceApi getDebugApi(long key) {
        if (!debugApis.containsKey(key)) {
            debugApis.put(key, new RadioReferenceApiImpl(key, true));
        }
        return debugApis.get(key);
    }
}

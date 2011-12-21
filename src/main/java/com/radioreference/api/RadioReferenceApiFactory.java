package com.radioreference.api;

public class RadioReferenceApiFactory {

    private static RadioReferenceApi api;

    public static RadioReferenceApi getApi(long key) {
        if (api == null) {
            api = new RadioReferenceApiImpl(key);
        }
        return api;
    }
}

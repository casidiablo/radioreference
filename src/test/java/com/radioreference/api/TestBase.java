package com.radioreference.api;

import org.junit.Before;

public class TestBase {

    protected RadioReferenceApi mApi;

    @Before
    public void setup() {
        mApi = RadioReferenceApiFactory.getApi(65701177);
    }
}

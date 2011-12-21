package com.radioreference.api;

import org.junit.Before;

public class TestBase {

    protected RadioReferenceApi mApi;

    @Before
    public void setup() {
        mApi = new RadioReferenceApiImpl(65701177, RadioReferenceApiImpl.Type.XML);
    }
}

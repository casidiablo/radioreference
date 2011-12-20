package com.radioreference.api;

public class FeedsFilter {
    private long stateId;
    private long countryId;
    private int top;
    private int newLimit;

    public FeedsFilter setStateId(long stateId) {
        this.stateId = stateId;
        return this;
    }

    public FeedsFilter setCountryId(long countryId) {
        this.countryId = countryId;
        return this;
    }

    public FeedsFilter setTop(int top) {
        this.top = top;
        return this;
    }

    public FeedsFilter setNew(int newLimit) {
        this.newLimit = newLimit;
        return this;
    }
}

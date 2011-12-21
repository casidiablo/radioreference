package com.radioreference.api;

public class FeedsFilter {
    private long stateId;
    private long countryId;
    private int top;
    private int newLimit;

    public FeedsFilter setStateId(long stateId) {
        this.stateId = stateId;
        if (countryId != 0) {
            throw new IllegalStateException("Cannot set state and country filter at the same time");
        }
        return this;
    }

    public FeedsFilter setCountryId(long countryId) {
        this.countryId = countryId;
        if (stateId != 0) {
            throw new IllegalStateException("Cannot set state and country filter at the same time");
        }
        return this;
    }

    public FeedsFilter setTop(int top) {
        this.top = top;
        if (newLimit != 0) {
            throw new IllegalStateException("Cannot set top and new filter at the same time");
        }
        return this;
    }

    public FeedsFilter setNew(int newLimit) {
        this.newLimit = newLimit;
        if (top != 0) {
            throw new IllegalStateException("Cannot set top and new filter at the same time");
        }
        return this;
    }

    public long getStateId() {
        return stateId;
    }

    public long getCountryId() {
        return countryId;
    }

    public int getTop() {
        return top;
    }

    public int getNewLimit() {
        return newLimit;
    }

    @Override
    public String toString() {
        return "FeedsFilter{" +
                "stateId=" + stateId +
                ", countryId=" + countryId +
                ", top=" + top +
                ", newLimit=" + newLimit +
                '}';
    }
}

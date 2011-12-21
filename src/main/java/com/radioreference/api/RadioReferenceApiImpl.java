package com.radioreference.api;

import com.radioreference.model.Country;
import com.radioreference.model.County;
import com.radioreference.model.Feed;
import com.radioreference.model.State;

import java.io.InputStream;
import java.util.List;

class RadioReferenceApiImpl implements RadioReferenceApi {

    public enum Type {
        XML, JSON
    }

    private final Type mType;
    private final HttpClient mExecutor;
    private final long mKey;

    public RadioReferenceApiImpl(long key, Type type) {
        mKey = key;
        mType = type;
        mExecutor = HttpClient.getInstance();
    }

    public List<Feed> getFeeds(FeedsFilter filter) {
        // prepare request
        Request request = getDefaultRequest();
        request.addParameter(Constants.PARAM_ACTION, Constants.ACTION_FEEDS);
        //apply filters if necessary
        if (filter != null) {
            if (filter.getStateId() > 0) {
                request.addParameter(Constants.PARAM_STATE_ID, String.valueOf(filter.getStateId()));
            }
            if (filter.getCountryId() > 0) {
                request.addParameter(Constants.PARAM_COUNTRY_ID, String.valueOf(filter.getCountryId()));
            }
            if (filter.getNewLimit() > 0) {
                request.addParameter(Constants.PARAM_NEW_LIMIT, String.valueOf(filter.getNewLimit()));
            }
            if (filter.getTop() > 0) {
                request.addParameter(Constants.PARAM_TOP, String.valueOf(filter.getTop()));
            }
        }
        // execute request and parse response
        Response.Feeds feedsResponse = XmlHelper.fromXml(Response.Feeds.class, executeRequest(request));
        return feedsResponse.getFeeds();
    }

    @Override
    public List<Country> getCountries() {
        // prepare request
        Request request = getDefaultRequest();
        request.addParameter(Constants.PARAM_ACTION, Constants.ACTION_COUNTRIES);
        // execute request and parse response
        Response.Countries countriesResponse = XmlHelper.fromXml(Response.Countries.class, executeRequest(request));
        return countriesResponse.getCountries();
    }

    @Override
    public List<State> getStates(long countryId) {
        // prepare request
        Request request = getDefaultRequest();
        request.addParameter(Constants.PARAM_ACTION, Constants.ACTION_STATES);
        request.addParameter(Constants.PARAM_COUNTRY_ID, String.valueOf(countryId));
        // execute request and parse response
        Response.States states = XmlHelper.fromXml(Response.States.class, executeRequest(request));
        return states.getStates();
    }

    @Override
    public List<County> getCounties(long stateId) {
        return null;
    }

    @Override
    public County getCounty(long countyId) {
        return null;
    }

    @Override
    public Feed getFeed(long feedId) {
        return null;
    }

    private InputStream executeRequest(Request request) {
        try {
            return mExecutor.execute(request);
        } catch (Exception e) {
            return null;
        }
    }

    private Request getDefaultRequest() {
        Request request = new Request(HttpClient.RequestMethod.GET, Constants.URL, Constants.DEFAULT_METHOD);
        request.addParameter(Constants.PARAM_KEY, String.valueOf(mKey));
        request.addParameter(Constants.PARAM_TYPE, mType.name().toLowerCase());
        return request;
    }
}

package com.radioreference.api;

import com.radioreference.model.Country;
import com.radioreference.model.County;
import com.radioreference.model.Feed;
import com.radioreference.model.State;

import java.io.InputStream;
import java.util.List;

class RadioReferenceApiImpl implements RadioReferenceApi {

    private final HttpClient mExecutor;
    private final long mKey;

    RadioReferenceApiImpl(long key) {
        mKey = key;
        mExecutor = HttpClient.getInstance();
    }

    public List<Feed> getFeeds(FeedsFilter filter) {
        // prepare request (apply filters if necessary)
        Request request = getDefaultRequest(Constants.ACTION_FEEDS);
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
        return feedsResponse == null ? null : feedsResponse.getFeeds();
    }

    @Override
    public List<Country> getCountries() {
        // prepare request, execute request and parse response
        Request request = getDefaultRequest(Constants.ACTION_COUNTRIES);
        Response.Countries countriesResponse = XmlHelper.fromXml(Response.Countries.class, executeRequest(request));
        return countriesResponse == null ? null : countriesResponse.getCountries();
    }

    @Override
    public List<State> getStates(long countryId) {
        // prepare request, execute request and parse response
        Request request = getDefaultRequest(Constants.ACTION_STATES);
        request.addParameter(Constants.PARAM_COUNTRY_ID, String.valueOf(countryId));
        Response.States states = XmlHelper.fromXml(Response.States.class, executeRequest(request));
        return states == null ? null : states.getStates();
    }

    @Override
    public List<County> getCounties(long stateId) {
        // prepare request, execute request and parse response
        Request request = getDefaultRequest(Constants.ACTION_COUNTIES);
        request.addParameter(Constants.PARAM_STATE_ID, String.valueOf(stateId));
        Response.Counties counties = XmlHelper.fromXml(Response.Counties.class, executeRequest(request));
        return counties == null ? null : counties.getCounties();
    }

    @Override
    public County getCounty(long countyId) {
        // prepare request, execute request and parse response
        Request request = getDefaultRequest(Constants.ACTION_COUNTY);
        request.addParameter(Constants.PARAM_COUNTY_ID, String.valueOf(countyId));
        return XmlHelper.fromXml(County.class, executeRequest(request));
    }

    @Override
    public Feed getFeed(long feedId) {
        // prepare request, execute request and parse response
        Request request = getDefaultRequest(Constants.ACTION_FEED);
        request.addParameter(Constants.PARAM_FEED_ID, String.valueOf(feedId));
        return XmlHelper.fromXml(Feed.class, executeRequest(request));
    }

    private InputStream executeRequest(Request request) {
        try {
            return mExecutor.execute(request);
        } catch (Exception e) {
            return null;
        }
    }

    private Request getDefaultRequest(String action) {
        Request request = new Request(HttpClient.RequestMethod.GET, Constants.URL, Constants.DEFAULT_METHOD);
        request.addParameter(Constants.PARAM_KEY, String.valueOf(mKey));
        request.addParameter(Constants.PARAM_TYPE, Constants.DEFAULT_TYPE);
        request.addParameter(Constants.PARAM_ACTION, action);
        return request;
    }
}

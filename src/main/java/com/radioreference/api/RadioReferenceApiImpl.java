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
        HttpClient.Request request = getDefaultRequest();
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
        // execute request
        InputStream inputStream = executeRequest(request);
        // parse response
        FeedsResponse feedsResponse = XmlHelper.fromXml(FeedsResponse.class, inputStream);
        return feedsResponse.getFeeds();
    }

    @Override
    public List<Country> getCountries() {
        return null;
    }

    @Override
    public List<State> getStates(long countryId) {
        return null;
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

    private InputStream executeRequest(HttpClient.Request request) {
        try {
            return mExecutor.execute(request);
        } catch (Exception e) {
            return null;
        }
    }

    private HttpClient.Request getDefaultRequest() {
        HttpClient.Request request = new HttpClient.Request(HttpClient.RequestMethod.GET, Constants.URL, Constants.DEFAULT_METHOD);
        request.addParameter(Constants.PARAM_KEY, String.valueOf(mKey));
        request.addParameter(Constants.PARAM_TYPE, mType.name().toLowerCase());
        return request;
    }
}

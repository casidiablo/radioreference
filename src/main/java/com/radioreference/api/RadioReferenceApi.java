package com.radioreference.api;

import com.radioreference.model.Country;
import com.radioreference.model.County;
import com.radioreference.model.Feed;
import com.radioreference.model.State;

import java.util.List;

public interface RadioReferenceApi {
    /**
     * @param filter used to determine what feeds should be fetched
     * @return all feeds that matches the specified filter. If filter is null, returns all feeds.
     */
    public List<Feed> getFeeds(FeedsFilter filter);

    /**
     * @return all countries that have audio feeds
     */
    public List<Country> getCountries();

    /**
     * @param countryId country ID
     * @return all states in a country, specified with parameter "coid", that have
     *         audio feeds
     */
    public List<State> getStates(long countryId);

    /**
     * @param stateId the id of the state of the counties to fetch
     * @return all counties (or equivalent entity) in a state, specified with
     *         parameter "stateId", that have audio feeds
     */
    public List<County> getCounties(long stateId);

    /**
     * @param countyId the id of the county to fetch
     * @return all audio feeds present in a county
     */
    public County getCounty(long countyId);

    /**
     * @param feedId individual Feed ID
     * @return details on an individual feed
     */
    public Feed getFeed(long feedId);
}

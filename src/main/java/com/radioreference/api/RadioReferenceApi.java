package com.radioreference.api;

public interface RadioReferenceApi {
    /**
     * @return all feeds
     */
    public String getFeeds();

    /**
     * @param filter used to determine what feeds should be fetched
     * @return all feeds that matches the specified filter
     */
    public String getFeeds(FeedsFilter filter);

    /**
     * @return all countries that have audio feeds
     */
    public String getCountries();

    /**
     * @param countryId country ID
     * @return all states in a country, specified with parameter "coid", that have
     *         audio feeds
     */
    public String getStates(long countryId);

    /**
     * @param stateId the id of the state of the counties to fetch
     * @return all counties (or equivalent entity) in a state, specified with
     *         parameter "stateId", that have audio feeds
     */
    public String getCounties(long stateId);

    /**
     * @param countyId the id of the county to fetch
     * @return all audio feeds present in a county
     */
    public String getCounty(long countyId);

    /**
     * @param feedId individual Feed ID
     * @return details on an individual feed
     */
    public String getFeed(long feedId);

    /**
     * @param feedId individual Feed ID
     * @return all dates that a feed has archives available / or links to all
     *         archives for a specific date
     */
    public String getArchives(long feedId);
}

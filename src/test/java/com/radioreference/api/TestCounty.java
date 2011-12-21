package com.radioreference.api;

import com.radioreference.model.County;
import com.radioreference.model.Feed;
import com.radioreference.model.Relay;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestCounty extends TestBase {
    @Test
    public void testCounty() {
        County county = mApi.getCounty(2537);
        assertNotNull(county);
        assertFalse(county.getFeeds().isEmpty());
        for (Feed feed : county.getFeeds()) {
            assertNotNull(feed);
            assertNotNull(feed.getId());
            assertNotNull(feed.getStatus());
            assertNotNull(feed.getListeners());
            assertNotNull(feed.getDescription());
            assertNotNull(feed.getGenre());
            assertNotNull(feed.getBitRate());
            assertNotNull(feed.getRelays());
            for (Relay relay : feed.getRelays()) {
                assertNotNull(relay);
                assertNotNull(relay.getHost());
                assertNotNull(relay.getPort());
            }
        }
    }
}
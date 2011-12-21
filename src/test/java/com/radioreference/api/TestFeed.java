package com.radioreference.api;

import com.radioreference.model.County;
import com.radioreference.model.Feed;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class TestFeed extends TestBase {
    @Test
    public void testFeed() {
        Feed feed = mApi.getFeed(100);
        assertNotNull(feed);
        assertEquals(100, feed.getId());
        assertEquals(1, feed.getStatus());
        assertNotNull(feed.getListeners());
        assertEquals("Columbus Police and Fire", feed.getDescription());
        assertEquals("Public Safety", feed.getGenre());
        assertEquals("/columbus", feed.getMount());
        assertEquals(16, feed.getBitRate());

        assertNotNull(feed.getCounties());
        for (County county : feed.getCounties()) {
            assertEquals(488, county.getCountyId());
            assertEquals("Muscogee", county.getName());
            assertEquals("County", county.getType());
            assertEquals(13, county.getStateId());
            assertEquals("GA", county.getStateCode());
            assertEquals("Georgia", county.getStateName());
            assertEquals("United States", county.getCountryName());
            assertEquals("US", county.getCountryCode());
            assertEquals(1, county.getCountryId());
            assertEquals("", county.getCountyDetails());
            assertEquals("32.5006966", county.getLatitude());
            assertEquals("-84.8665971", county.getLongitude());
        }
    }
}

package com.radioreference.api;

import com.radioreference.model.County;
import com.radioreference.model.Feed;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestFeeds extends TestBase {
    @Test
    public void testFeeds() {
        List<Feed> feeds = mApi.getFeeds(null);
        assertNotNull(feeds);
        assertTrue(feeds.size() > 0);
        for (Feed feed : feeds) {
            assertNotNull(feed);
            assertNotNull(feed.getCounties());
            for (County county : feed.getCounties()) {
                assertNotNull(county);
            }
        }
    }

    @Test
    public void testFeedsFilteredByCountry() {
        FeedsFilter filter = new FeedsFilter().setCountryId(1);
        List<Feed> usaFeeds = mApi.getFeeds(filter);
        assertNotNull(usaFeeds);
        assertFalse(usaFeeds.isEmpty());

        for (Feed usaFeed : usaFeeds) {
            List<County> counties = usaFeed.getCounties();
            assertFalse(counties.isEmpty());
            for (County county : counties) {
                assertEquals(1L, county.getCountryId());
                assertEquals("United States", county.getCountryName());
                assertEquals("US", county.getCountryCode());
            }
        }
    }

    @Test
    public void testFeedsFilteredByState() {
        FeedsFilter filter = new FeedsFilter().setStateId(110);
        List<Feed> princeEdwardIslandFeeds = mApi.getFeeds(filter);
        assertNotNull(princeEdwardIslandFeeds);
        assertFalse(princeEdwardIslandFeeds.isEmpty());

        for (Feed princeEdwardIslandFeed : princeEdwardIslandFeeds) {
            List<County> counties = princeEdwardIslandFeed.getCounties();
            assertFalse(counties.isEmpty());
            for (County county : counties) {
                assertEquals(2L, county.getCountryId());
                assertEquals("Canada", county.getCountryName());
                assertEquals("CA", county.getCountryCode());
                assertEquals(110, county.getStateId());
                assertEquals("PE", county.getStateCode());
                assertEquals("Prince Edward Island", county.getStateName());
            }
        }
    }

    @Test
    public void testFeedsSortedByTop() {
        FeedsFilter filter = new FeedsFilter().setTop(20).setCountryId(1);

        List<Feed> topUsaFeeds = mApi.getFeeds(filter);
        assertNotNull(topUsaFeeds);
        assertEquals(20, topUsaFeeds.size());

        int lastListenerMark = Integer.MAX_VALUE;
        for (Feed feed : topUsaFeeds) {
            // it must be sorted by listeners
            assertTrue(lastListenerMark >= feed.getListeners());
            lastListenerMark = feed.getListeners();

            // county must belong to USA
            List<County> counties = feed.getCounties();
            assertFalse(counties.isEmpty());
            for (County county : counties) {
                assertEquals(1L, county.getCountryId());
                assertEquals("United States", county.getCountryName());
                assertEquals("US", county.getCountryCode());
            }
        }
    }

    @Test
    public void testFeedsSortedByDate() {
        FeedsFilter filter = new FeedsFilter().setNew(20).setCountryId(1);

        List<Feed> newUsaFeeds = mApi.getFeeds(filter);
        assertNotNull(newUsaFeeds);
        assertEquals(20, newUsaFeeds.size());

        int lastListenerMark = Integer.MAX_VALUE;
        for (Feed feed : newUsaFeeds) {
            // county must belong to USA
            List<County> counties = feed.getCounties();
            assertFalse(counties.isEmpty());
            for (County county : counties) {
                assertEquals(1L, county.getCountryId());
                assertEquals("United States", county.getCountryName());
                assertEquals("US", county.getCountryCode());
            }
        }
    }

    @Test(expected = IllegalStateException.class)
    public void mustFailWhenSettingTopAndNewFilter() {
        mApi.getFeeds(new FeedsFilter().setTop(1).setNew(1));
    }

    @Test(expected = IllegalStateException.class)
    public void mustFailWhenSettingStateAndCountryFilter() {
        mApi.getFeeds(new FeedsFilter().setCountryId(1).setStateId(1));
    }
}

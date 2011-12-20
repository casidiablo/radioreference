package com.radioreference.api;

import com.radioreference.model.Feed;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feeds")
class FeedsResponse {
    @ElementList(inline = true, required = true, entry = "feed", type = Feed.class)
    private List<Feed> feeds;

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedsResponse)) return false;

        FeedsResponse that = (FeedsResponse) o;

        if (feeds != null ? !feeds.equals(that.feeds) : that.feeds != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return feeds != null ? feeds.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FeedsResponse{" +
                "feeds=" + feeds +
                '}';
    }
}

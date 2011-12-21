package com.radioreference.api;

import com.radioreference.model.Country;
import com.radioreference.model.County;
import com.radioreference.model.Feed;
import com.radioreference.model.State;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

class Response {
    @Root(name = "feeds")
    static class Feeds {
        @ElementList(inline = true, required = true, entry = "feed", type = Feed.class)
        private List<Feed> feeds;

        public List<Feed> getFeeds() {
            return feeds;
        }

        @Override
        public String toString() {
            return "{feeds=" + feeds + '}';
        }
    }

    @Root
    static class Countries {
        @ElementList(inline = true, required = true, entry = "country", type = Country.class)
        private List<Country> countries;

        public List<Country> getCountries() {
            return countries;
        }

        @Override
        public String toString() {
            return "{countries=" + countries + '}';
        }
    }

    @Root
    static class States {
        @ElementList(inline = true, required = true, entry = "state", type = State.class)
        private List<State> states;

        public List<State> getStates() {
            return states;
        }

        @Override
        public String toString() {
            return "{states=" + states + '}';
        }
    }

    @Root
    static class Counties {
        @ElementList(inline = true, required = true, entry = "county", type = County.class)
        private List<County> counties;

        public List<County> getCounties() {
            return counties;
        }

        @Override
        public String toString() {
            return "{counties=" + counties + '}';
        }
    }
}

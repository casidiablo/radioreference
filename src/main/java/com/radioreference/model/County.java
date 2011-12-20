package com.radioreference.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class County {
    @Attribute(name = "ctid")
    private long id;
    @Attribute(name = "name")
    private String name;
    @Attribute(name = "type")
    private String type;
    @Attribute(name = "stid")
    private long stateId;
    @Attribute(name = "stateCode")
    private String stateCode;
    @Attribute(name = "stateName")
    private String stateName;
    @Attribute(name = "countryName")
    private String countryName;
    @Attribute(name = "countryCode")
    private String countryCode;
    @Attribute(name = "coid")
    private long countryId;
    @Attribute(name = "countyDetails")
    private String countyDetails;
    @Attribute(name = "lat", required = false)
    private String latitude;
    @Attribute(name = "lon", required = false)
    private String longitude;

    @ElementList(name = "feeds", inline = false, entry = "feed", type = Feed.class, required = false)
    private List<Feed> feeds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountyDetails() {
        return countyDetails;
    }

    public void setCountyDetails(String countyDetails) {
        this.countyDetails = countyDetails;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof County)) return false;

        County county = (County) o;

        if (countryId != county.countryId) return false;
        if (id != county.id) return false;
        if (stateId != county.stateId) return false;
        if (countryCode != null ? !countryCode.equals(county.countryCode) : county.countryCode != null) return false;
        if (countryName != null ? !countryName.equals(county.countryName) : county.countryName != null) return false;
        if (countyDetails != null ? !countyDetails.equals(county.countyDetails) : county.countyDetails != null)
            return false;
        if (feeds != null ? !feeds.equals(county.feeds) : county.feeds != null) return false;
        if (latitude != null ? !latitude.equals(county.latitude) : county.latitude != null) return false;
        if (longitude != null ? !longitude.equals(county.longitude) : county.longitude != null) return false;
        if (name != null ? !name.equals(county.name) : county.name != null) return false;
        if (stateCode != null ? !stateCode.equals(county.stateCode) : county.stateCode != null) return false;
        if (stateName != null ? !stateName.equals(county.stateName) : county.stateName != null) return false;
        if (type != null ? !type.equals(county.type) : county.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (stateId ^ (stateId >>> 32));
        result = 31 * result + (stateCode != null ? stateCode.hashCode() : 0);
        result = 31 * result + (stateName != null ? stateName.hashCode() : 0);
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (int) (countryId ^ (countryId >>> 32));
        result = 31 * result + (countyDetails != null ? countyDetails.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (feeds != null ? feeds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", stateId=" + stateId +
                ", stateCode='" + stateCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryId=" + countryId +
                ", countyDetails='" + countyDetails + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", feeds=" + feeds +
                '}';
    }
}

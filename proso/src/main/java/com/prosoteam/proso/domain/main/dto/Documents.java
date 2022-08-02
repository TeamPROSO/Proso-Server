package com.prosoteam.proso.domain.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Documents {
    @JsonProperty("y")
    private String y;
    @JsonProperty("x")
    private String x;
    @JsonProperty("road_address_name")
    private String roadAddressName;
    @JsonProperty("place_url")
    private String placeUrl;
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("id")
    private String id;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("category_group_name")
    private String categoryGroupName;
    @JsonProperty("category_group_code")
    private String categoryGroupCode;
    @JsonProperty("address_name")
    private String addressName;

    public String getY() {
        return y;
    }

    public String getX() {
        return x;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getDistance() {
        return distance;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryGroupName() {
        return categoryGroupName;
    }

    public String getCategoryGroupCode() {
        return categoryGroupCode;
    }

    public String getAddressName() {
        return addressName;
    }
}

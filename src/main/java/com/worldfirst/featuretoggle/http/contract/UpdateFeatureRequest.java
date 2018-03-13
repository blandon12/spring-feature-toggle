package com.worldfirst.featuretoggle.http.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateFeatureRequest {
    private final String description;

    public UpdateFeatureRequest(@JsonProperty("description") String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

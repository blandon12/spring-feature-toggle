package com.worldfirst.featuretoggle.http.contract;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFeatureRequest {
    private final String id;
    private final String description;

    @JsonCreator
    public CreateFeatureRequest(
            @JsonProperty("id") String id,
            @JsonProperty("description") String description
    ) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

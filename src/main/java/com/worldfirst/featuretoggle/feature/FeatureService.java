package com.worldfirst.featuretoggle.feature;

import com.worldfirst.featuretoggle.feature.exception.FeatureDeletedException;

import java.util.Collection;
import java.util.Optional;

public interface FeatureService {

    void createFeature(String id, String description);

    Optional<Feature> findOne(String id);

    Feature updateDescription(String id, String newDescription) throws FeatureDeletedException;

    void delete(String id) throws FeatureDeletedException;

    Iterable<Feature> findAll();
}

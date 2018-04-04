package com.worldfirst.featuretoggle.feature;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, String> {
    Optional<Feature> findById(String id);
}

package com.worldfirst.featuretoggle.feature;

import org.springframework.stereotype.Service;

@Service
public class FeatureServiceImpl implements FeatureService {

    private FeatureRepository featureRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public void createFeature(String id, String description) {
        Feature feature = new Feature(id, description);

        featureRepository.save(feature);
    }
}

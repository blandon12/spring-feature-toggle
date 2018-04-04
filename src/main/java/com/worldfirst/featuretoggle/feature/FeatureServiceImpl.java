package com.worldfirst.featuretoggle.feature;

import com.worldfirst.featuretoggle.feature.exception.FeatureDeletedException;
import com.worldfirst.featuretoggle.http.contract.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureServiceImpl implements FeatureService {

    private FeatureRepository featureRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public void createFeature(String id, String description) {
        Feature feature = new Feature(id, description);

        featureRepository.save(feature);
    }

    @Override
    public Optional<Feature> findOne(String id) {
        return featureRepository.findById(id);
    }

    @Override
    public Feature updateDescription(String id, String newDescription) throws FeatureDeletedException {
        Optional<Feature> featureOptional = findOne(id);

        Feature feature = featureOptional.orElseThrow(() -> {
            return new EntityNotFoundException();
        });

        feature.updateDescription(newDescription);

        featureRepository.save(feature);

        return feature;
    }

    @Override
    public void delete(String id) throws FeatureDeletedException {
        Feature feature = findOne(id).orElseThrow(EntityNotFoundException::new);
        feature.delete();

        featureRepository.save(feature);
    }

    @Override
    public Iterable<Feature> findAll() {
        return featureRepository.findAll();
    }
}

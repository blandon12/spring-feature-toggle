package com.worldfirst.featuretoggle.feature;

import com.worldfirst.featuretoggle.feature.exception.FeatureDeletedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integration-tests")
public class FeatureServiceImplIT {
    
    @Autowired
    FeatureService featureService;

    @Test
    public void createFeatureProperty() {
        // Given
        final String FEATURE_ID = "testId";
        final String FEATURE_DESCRIPTION = "test description";

        // When
        featureService.createFeature(FEATURE_ID, FEATURE_DESCRIPTION);

        // Then
        Optional<Feature> optionalFeature = featureService.findOne(FEATURE_ID);
        assertTrue(optionalFeature.isPresent());
    }


    @Test
    public void producesTheRightFeatureWhenFindOneAndTheFeatureIsPresent() {

    }

    @Test
    public void updateDescriptionProperly() throws FeatureDeletedException {

    }
}

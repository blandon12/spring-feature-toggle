package com.worldfirst.featuretoggle.feature;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeatureServiceImplTest {

    @Mock
    FeatureRepository featureRepository;

    FeatureService featureService;

    @Before
    public void setUp() throws Exception {
        featureService = new FeatureServiceImpl(featureRepository);
    }

    @Test
    public void producesEmptyWhenFindOneAndFeatureIsNotPresent() {

        // given
        final String FEATURE_ID = "testId";
        when(featureRepository.findById(FEATURE_ID)).thenReturn(Optional.empty());

        // when
        Optional<Feature> optionalFeature = featureService.findOne(FEATURE_ID);

        //then
        assertFalse(optionalFeature.isPresent());
    }

    @Test
    public void producesEmptyWhenFindOneAndFeatureIsPresent() {

        // given
        final String FEATURE_ID = "testId";
        final String FEATURE_DESCRIPTION = "testDescription";
        final Feature feature = new Feature(FEATURE_ID, FEATURE_DESCRIPTION);

        when(featureRepository.findById(FEATURE_ID)).thenReturn(Optional.of(feature));

        // when
        Optional<Feature> optionalFeature = featureService.findOne(FEATURE_ID);

        //then
        assertEquals(feature, optionalFeature.get());
    }

    @Test
    public void createFeature() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void updateDescription() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findAll() {
    }
}
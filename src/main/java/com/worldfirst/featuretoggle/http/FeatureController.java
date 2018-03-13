package com.worldfirst.featuretoggle.http;

import com.worldfirst.featuretoggle.feature.Feature;
import com.worldfirst.featuretoggle.feature.FeatureRepository;
import com.worldfirst.featuretoggle.http.contract.CreateFeatureRequest;
import com.worldfirst.featuretoggle.http.contract.EntityNotFoundException;
import com.worldfirst.featuretoggle.http.contract.ErrorResponse;
import com.worldfirst.featuretoggle.http.contract.UpdateFeatureRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class FeatureController {

    private FeatureRepository featureRepository;

    public FeatureController(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @GetMapping("/api/features")
    public Iterable<Feature> findAll() {
        return featureRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/features")
    public void create(@RequestBody CreateFeatureRequest request) {
        Feature feature = new Feature(
//                UUID.randomUUID().toString(),
//                "Random Feature"
                request.getId(),
                request.getDescription()
        );

        featureRepository.save(feature);
    }

    @GetMapping("/api/features/{featureId}")
    public Feature getOne(@PathVariable("featureId") String featureId) {
        Feature feature = featureRepository.findOne(featureId);

        if (feature != null) {
            return feature;
        }

        throw new EntityNotFoundException();
    }

    @PutMapping("/api/features/{featureId}")
    public Feature updateFeature(
            @PathVariable("featureId") String featureId,
            @RequestBody UpdateFeatureRequest request
    ) {
        Feature feature = featureRepository.findOne(featureId);

        if (feature != null) {
            feature.updateDescription(request.getDescription());
            featureRepository.save(feature);

            return feature;
        }

        throw new EntityNotFoundException();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleInvalidJson() {
        return new ErrorResponse("Bad JSON");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Object not found");
    }
}

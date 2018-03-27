package com.worldfirst.featuretoggle.http;

import com.worldfirst.featuretoggle.feature.Feature;
import com.worldfirst.featuretoggle.feature.FeatureRepository;
import com.worldfirst.featuretoggle.feature.FeatureService;
import com.worldfirst.featuretoggle.feature.exception.FeatureDeletedException;
import com.worldfirst.featuretoggle.http.contract.UnprocessableEntityException;
import com.worldfirst.featuretoggle.http.contract.CreateFeatureRequest;
import com.worldfirst.featuretoggle.http.contract.EntityNotFoundException;
import com.worldfirst.featuretoggle.http.contract.ErrorResponse;
import com.worldfirst.featuretoggle.http.contract.UpdateFeatureRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeatureController {

    private FeatureRepository featureRepository;
    private FeatureService featureService;

    public FeatureController(FeatureRepository featureRepository, FeatureService featureService) {
        this.featureRepository = featureRepository;
        this.featureService = featureService;
    }

    @GetMapping("/api/features")
    public Iterable<Feature> findAll() {
        return featureRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/features")
    public void create(@RequestBody CreateFeatureRequest request) {

        featureService.createFeature(request.getId(), request.getDescription());
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
            try {
                feature.updateDescription(request.getDescription());
            } catch (FeatureDeletedException e) {
                throw new EntityNotFoundException();
            }
            featureRepository.save(feature);

            return feature;
        }

        throw new EntityNotFoundException();
    }

//    @DeleteMapping("/api/features/{featureId}")
    @RequestMapping(
            path = "/api/features/{featureId}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeature(@PathVariable("featureId") String featureId) {
        Feature feature = featureRepository.findOne(featureId);

        if (feature == null) {
            throw new EntityNotFoundException();
        }

        try {
            feature.delete();
        } catch (FeatureDeletedException e) {
            throw new UnprocessableEntityException();
        }
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

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ErrorResponse unprossableEntity() {
        return new ErrorResponse("Change in entity could not be applied");
    }
}

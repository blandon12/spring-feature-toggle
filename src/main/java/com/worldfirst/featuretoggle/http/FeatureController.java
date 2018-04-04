package com.worldfirst.featuretoggle.http;

import com.worldfirst.featuretoggle.feature.Feature;
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

    private FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping("/api/features")
    public Iterable<Feature> findAll() {
        return featureService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/features")
    public void create(@RequestBody CreateFeatureRequest request) {

        featureService.createFeature(request.getId(), request.getDescription());
    }

    @GetMapping("/api/features/{featureId}")
    public Feature getOne(@PathVariable("featureId") String featureId) {
        return featureService.findOne(featureId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping("/api/features/{featureId}")
    public Feature updateFeature(
            @PathVariable("featureId") String featureId,
            @RequestBody UpdateFeatureRequest request
    ) {
        try {
            return featureService.updateDescription(featureId, request.getDescription());
        } catch (FeatureDeletedException e) {
            throw new EntityNotFoundException();
        }
    }

//    @DeleteMapping("/api/features/{featureId}")
    @RequestMapping(
            path = "/api/features/{featureId}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeature(@PathVariable("featureId") String featureId) {
        try {
            featureService.delete(featureId);
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

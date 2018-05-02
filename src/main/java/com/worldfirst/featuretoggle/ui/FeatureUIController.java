package com.worldfirst.featuretoggle.ui;

import com.worldfirst.featuretoggle.feature.FeatureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeatureUIController {

    private FeatureService featureService;

    public FeatureUIController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping("/ui/features")
    public String listFeatures(Model model) {

        model.addAttribute("header", "List of features");

        model.addAttribute("features", featureService.findAll());

        return "features/list";
    }
}

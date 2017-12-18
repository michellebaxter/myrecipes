package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Source;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sources")
public class SourceController {

    private SourceRepository sourceRepository;

    public SourceController(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @GetMapping
    public List<Source> getSources() {
        return sourceRepository.findAll();
    }
}

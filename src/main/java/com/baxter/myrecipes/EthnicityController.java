package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Ethnicity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ethnicities")
public class EthnicityController {

    private EthnicityRepository ethnicityRepository;

    public EthnicityController(EthnicityRepository ethnicityRepository) {
        this.ethnicityRepository = ethnicityRepository;
    }

    @GetMapping
    public List<Ethnicity> getEthnicities() {
        return ethnicityRepository.findAll();
    }
}

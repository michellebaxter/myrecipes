package com.baxter.myrecipes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/years")
public class YearController {

    private List<Integer> years;

    static final int firstYear = 1988;

    public YearController() {
        Year thisYear = Year.now();
        years = new ArrayList<>();
        for (int i = firstYear; i <= thisYear.getValue(); i++) {
            years.add(i);
        }
    }

    @GetMapping
    public List<Integer> getYears() {
        return years;
    }
}

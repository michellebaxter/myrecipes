package com.baxter.myrecipes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/months")
public class MonthController {

    private List<List<String>> months;

    public MonthController() {
        months = new ArrayList<>(12);
        for (Month month : Month.values()) {
            List<String> monthInfo = new ArrayList<>(2);
            monthInfo.add(month.name());
            monthInfo.add(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            months.add(monthInfo);
        }
    }

    @GetMapping
    public List<List<String>> getMonths() {
        return months;
    }

}

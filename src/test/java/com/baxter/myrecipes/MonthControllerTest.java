package com.baxter.myrecipes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonthController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class MonthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMonths() throws Exception {

        mockMvc.perform(get("/months"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$.[0][0]", is("JANUARY")))
                .andExpect(jsonPath("$.[0][1]", is("January")))
                .andExpect(jsonPath("$.[1][0]", is("FEBRUARY")))
                .andExpect(jsonPath("$.[1][1]", is("February")))
                .andExpect(jsonPath("$.[2][0]", is("MARCH")))
                .andExpect(jsonPath("$.[2][1]", is("March")))
                .andExpect(jsonPath("$.[3][0]", is("APRIL")))
                .andExpect(jsonPath("$.[3][1]", is("April")))
                .andExpect(jsonPath("$.[4][0]", is("MAY")))
                .andExpect(jsonPath("$.[4][1]", is("May")))
                .andExpect(jsonPath("$.[5][0]", is("JUNE")))
                .andExpect(jsonPath("$.[5][1]", is("June")))
                .andExpect(jsonPath("$.[6][0]", is("JULY")))
                .andExpect(jsonPath("$.[6][1]", is("July")))
                .andExpect(jsonPath("$.[7][0]", is("AUGUST")))
                .andExpect(jsonPath("$.[7][1]", is("August")))
                .andExpect(jsonPath("$.[8][0]", is("SEPTEMBER")))
                .andExpect(jsonPath("$.[8][1]", is("September")))
                .andExpect(jsonPath("$.[9][0]", is("OCTOBER")))
                .andExpect(jsonPath("$.[9][1]", is("October")))
                .andExpect(jsonPath("$.[10][0]", is("NOVEMBER")))
                .andExpect(jsonPath("$.[10][1]", is("November")))
                .andExpect(jsonPath("$.[11][0]", is("DECEMBER")))
                .andExpect(jsonPath("$.[11][1]", is("December")))
        ;
    }

}

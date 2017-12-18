package com.baxter.myrecipes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(YearController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class YearControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetYears() throws Exception {

        mockMvc.perform(get("/years"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(30))))
                .andExpect(jsonPath("$.[0]", is(YearController.firstYear)))
                .andExpect(jsonPath("$.[1]", is(1989)))
                .andExpect(jsonPath("$.[2]", is(1990)))
                .andExpect(jsonPath("$.[3]", is(1991)))
                .andExpect(jsonPath("$.[4]", is(1992)))
                .andExpect(jsonPath("$.[5]", is(1993)))
                .andExpect(jsonPath("$.[6]", is(1994)))
                .andExpect(jsonPath("$.[7]", is(1995)))
                .andExpect(jsonPath("$.[8]", is(1996)))
                .andExpect(jsonPath("$.[9]", is(1997)))
                .andExpect(jsonPath("$.[10]", is(1998)))
                .andExpect(jsonPath("$.[11]", is(1999)))
        ;
    }

}

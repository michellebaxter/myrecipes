package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingRepository repository;

    @Test
    public void testGetRatings() throws Exception {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(Rating.builder().id(1L).description("No Rating").build());
        ratings.add(Rating.builder().id(2L).description("Good").value(4).build());
        given(repository.findAll()).willReturn(ratings);

        mockMvc.perform(get("/ratings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[0].description", is("No Rating")))
                .andExpect(jsonPath("$.[1].description", is("Good")))
                .andExpect(jsonPath("$.[0].value", nullValue()))
                .andExpect(jsonPath("$.[1].value", is(4)))
        ;
    }

}

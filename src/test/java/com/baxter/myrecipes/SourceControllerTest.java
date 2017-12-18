package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Source;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SourceController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class SourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SourceRepository repository;

    @Test
    public void testGetSources() throws Exception {
        List<Source> sources = new ArrayList<>();
        sources.add(Source.builder().id(1L).description("Food & Wine").build());
        sources.add(Source.builder().id(2L).description("Eating Well").build());
        given(repository.findAll()).willReturn(sources);

        mockMvc.perform(get("/sources"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[0].description", is("Food & Wine")))
                .andExpect(jsonPath("$.[1].description", is("Eating Well")))
        ;
    }

}

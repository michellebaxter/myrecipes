package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Ethnicity;
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

@WebMvcTest(EthnicityController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class EthnicityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EthnicityRepository repository;

    @Test
    public void testGetEthnicities() throws Exception {
        List<Ethnicity> ethnicities = new ArrayList<>();
        ethnicities.add(Ethnicity.builder().id(1L).description("French").build());
        ethnicities.add(Ethnicity.builder().id(2L).description("Italian").build());
        ethnicities.add(Ethnicity.builder().id(3L).description("Mexican").build());

        given(repository.findAll()).willReturn(ethnicities);

        mockMvc.perform(get("/ethnicities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[0].description", is("French")))
                .andExpect(jsonPath("$.[1].description", is("Italian")))
                .andExpect(jsonPath("$.[2].description", is("Mexican")))
        ;
    }

}

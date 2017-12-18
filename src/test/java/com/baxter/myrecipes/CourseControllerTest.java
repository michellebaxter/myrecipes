package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Course;
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

@WebMvcTest(CourseController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository repository;

    @Test
    public void testGetCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder().id(1L).description("Breakfast").build());
        courses.add(Course.builder().id(2L).description("Lunch").build());
        courses.add(Course.builder().id(3L).description("Dinner").build());

        given(repository.findAll()).willReturn(courses);

        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[0].description", is("Breakfast")))
                .andExpect(jsonPath("$.[1].description", is("Lunch")))
                .andExpect(jsonPath("$.[2].description", is("Dinner")))
        ;
    }

}

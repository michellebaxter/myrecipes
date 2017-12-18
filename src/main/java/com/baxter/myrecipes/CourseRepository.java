package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}

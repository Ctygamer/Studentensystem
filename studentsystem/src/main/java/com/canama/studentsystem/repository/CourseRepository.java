package com.canama.studentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.canama.studentsystem.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}

package com.canama.studentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.canama.studentsystem.Entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}

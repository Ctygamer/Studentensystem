package com.canama.studentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canama.studentsystem.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}

package com.canama.studentsystem.service;

import java.util.List;

import com.canama.studentsystem.model.Student;

// Interface for StudentService 
public interface StudentService {
    // Method to save student details to the database
    public Student saveStudent(Student student);

    // Method to get all students
    public List<Student> getAllStudents();

    // Method to Delete Student by id
    public void deleteStudentById(Integer id);
}

package com.canama.studentsystem.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.canama.studentsystem.model.Course;
import com.canama.studentsystem.model.Student;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Student saveStudent(Student student) {
        // Annahme: `getId()` ist durch ein entsprechendes Getter ersetzt, wenn der direkte Feldzugriff nicht möglich ist.
        if (student.getId() != 0) { // Prüft, ob eine ID vorhanden ist
            return updateStudentCourses(student);
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudentById(Integer id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.getCourses().clear(); // Bereinigt die Beziehung
        studentRepository.save(student); // Aktualisiert den Studenten ohne Kurse
        studentRepository.deleteById(id); // Löscht den Studenten
    }

    // Methode zum Abrufen eines Studenten nach ID
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // Methode zum Aktualisieren der Kurse eines Studenten
    private Student updateStudentCourses(Student student) {
        Student existingStudent = studentRepository.findById(student.getId())
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + student.getId()));

        // Set für effiziente Überprüfung vorhandener Kurse
        Set<Integer> currentCourseIds = new HashSet<>();
        for (Course course : existingStudent.getCourses()) {
            currentCourseIds.add(course.getId());
        }

        // Hinzufügen neuer Kurse und Bereinigung nicht mehr zugeordneter Kurse
        existingStudent.getCourses().removeIf(course -> !student.getCourses().contains(course));
        for (Course inputCourse : student.getCourses()) {
            if (!currentCourseIds.contains(inputCourse.getId())) {
                Course managedCourse = courseRepository.findById(inputCourse.getId())
                    .orElseThrow(() -> new RuntimeException("Course not found with id: " + inputCourse.getId()));
                existingStudent.getCourses().add(managedCourse);
            }
        }
        return studentRepository.save(existingStudent);
    }
}

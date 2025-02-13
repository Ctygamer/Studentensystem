package com.canama.studentsystem.controller;

import com.canama.studentsystem.model.Course;
import com.canama.studentsystem.model.Student;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    // ✅ Kurs hinzufügen
    @PostMapping("/add")
    public String addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return "Course added successfully";
    }

    // ✅ Alle Kurse abrufen
    @GetMapping("/getall")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // ✅ Kurs per ID löschen (mit Fehlerprüfung)
    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        if (!courseRepository.existsById(id)) {
            return "Course not found";
        }
        courseRepository.deleteById(id);
        return "Course deleted successfully";
    }

    // ✅ Student zu einem Kurs hinzufügen (Fehlerbehebung)
    @PutMapping("/{courseId}/add-student/{studentId}")
    public String addStudentToCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 🔥 Sicherstellen, dass die Liste nicht null ist
        if (course.getStudents() == null) {
            course.setStudents(new ArrayList<>()); // Initialisiert die Liste
        }

        // 🔥 Fix: ID-Vergleich statt `contains()`
        boolean alreadyExists = course.getStudents().stream()
                                      .anyMatch(s -> s.getId() == student.getId());

        if (!alreadyExists) {
            course.getStudents().add(student);
            courseRepository.save(course);
            return "Student added to course!";
        }

        return "Student is already in the course.";
    }

    // ✅ Student von einem Kurs entfernen (Fehlerbehebung) 
    @PutMapping("/{courseId}/update-student/{studentId}")
    public ResponseEntity<?> updateStudentCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
    Course newCourse = courseRepository.findById(courseId)
        .orElseThrow(() -> new RuntimeException("Course not found"));

    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new RuntimeException("Student not found"));

    // 🔥 Entfernt den Studenten aus allen alten Kursen
    student.getCourses().clear();

    // 🔥 Fügt den neuen Kurs hinzu
    student.getCourses().add(newCourse);

    studentRepository.save(student);

    return ResponseEntity.ok("Student updated to new course!");
}
}

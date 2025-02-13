package com.canama.studentsystem.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canama.studentsystem.model.Course;
import com.canama.studentsystem.model.Student;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.service.StudentServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



//Das ist ein RestController, der die Anfragen von der Frontendseite entgegennimmt und die Daten an die Service-Klasse weiterleitet.
@RestController
@RequestMapping("/student") //Der Pfad, der in der URL eingegeben wird, um auf die Methoden in diesem Controller zuzugreifen.
@CrossOrigin(origins = "http://localhost:3000")//Diese Annotation ermöglicht den Zugriff auf die Methoden in diesem Controller von einer anderen Domain.
public class StudentController {
    //Autowired ist eine Annotation, die Spring sagt, dass es die Instanzierung übernehmen soll
    @Autowired
    private StudentServiceImpl studentService; //Die Service-Klasse, die die Datenbankzugriffe durchführt.

    @Autowired  // ✅ Hier die fehlende Injektion für das Repository hinzufügen
    private CourseRepository courseRepository;  // 🔥 Kurs-Repository definieren
    //Diese Methode nimmt die Anfragen von der Frontendseite entgegen und leitet die Daten an die Service-Klasse weiter.
    //Die Service-Klasse führt die Datenbankzugriffe durch.
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody Student student) {
        try {
            // ✅ FIX: Hole Kurse aus der Datenbank basierend auf IDs
            List<Course> realCourses = student.getCourses().stream()
                .map(course -> courseRepository.findById(course.getId())
                    .orElseThrow(() -> new RuntimeException("Course not found: " + course.getId())))
                .collect(Collectors.toList());

            student.setCourses(realCourses);
            studentService.saveStudent(student);
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    
    //Diese Methode nimmt die Anfragen von der Frontendseite entgegen und leitet die Daten an die Service-Klasse weiter.
    @GetMapping("getall")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

     // ✅ Student per ID löschen
    @DeleteMapping("delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return "Student deleted successfully";
    }
    // ✅ Student per ID suchen und zurückgeben 
    @PutMapping("/{id}/update-courses")
    public ResponseEntity<Map<String, String>> updateStudentCourses(@PathVariable Integer id,
        @RequestBody List<Map<String, Integer>> coursesData) {
      try {
          Student student = studentService.getStudentById(id);
          List<Course> realCourses = coursesData.stream()
              .map(courseMap -> {
                  Integer courseId = courseMap.get("id");
                  return courseRepository.findById(courseId)
                             .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));
              })
              .collect(Collectors.toList());
      
          student.setCourses(realCourses);
          studentService.saveStudent(student);
          return ResponseEntity.ok(Map.of("message", "Student courses updated successfully"));
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
      }
    }
}

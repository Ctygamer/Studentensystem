package com.canama.studentsystem.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canama.studentsystem.Entity.Course;
import com.canama.studentsystem.Entity.Student;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.service.StudentServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


/**
 * Der `StudentController` ist ein REST-Controller, der HTTP-Anfragen von der Frontendseite
 * entgegennimmt und diese an die entsprechende Service-Klasse weiterleitet.
 * <p>
 * Die Annotationen `@RestController` und `@RequestMapping` dienen der Konfiguration des Controllers
 * sowie der Definition des Zugriffspfads. Anfragen können von einer anderen Domain zugelassen werden,
 * dank der `@CrossOrigin`-Annotation, die CORS (Cross-Origin Resource Sharing) erlaubt.
 * </p>
 */
@RestController
@RequestMapping("/student") //Der Pfad, der in der URL eingegeben wird, um auf die Methoden in diesem Controller zuzugreifen.
@RequiredArgsConstructor // Automatischer Konstrucktor für finale Felder
public class StudentController {
    /**
     * Service-Klasse für die Geschäftslogik und Datenbankzugriffe bezüglich Studenten.
     * Spring übernimmt automatisch die Instanziierung durch `@Autowired`.
     */
    private final StudentServiceImpl studentService; // finaler Feld für die Service-Komponente


    /**
     * Repository für den Zugriff auf Kursdaten.
     */
    private final CourseRepository courseRepository; // final Feld für das Repository


    /**
     * Fügt einen neuen Studenten in das System ein.
     *
     * @param student Der Student, der hinzugefügt werden soll.
     * @return Eine `ResponseEntity`, die den Status der Anfrage enthält.
     */
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

    /**
     * Gibt die Liste aller Studenten zurück.
     *
     * @return Eine Liste aller `Student`-Objekte.
     */
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Löscht einen Studenten anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Studenten.
     * @return Eine Nachricht, die den Erfolg oder Misserfolg des Löschvorgangs anzeigt.
     */
    @DeleteMapping("delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return "Student deleted successfully";
    }

    /**
     * Aktualisiert die Kurse eines Studenten anhand seiner ID.
     *
     * @param id Die ID des Studenten, dessen Kurse aktualisiert werden sollen.
     * @param coursesData Eine Liste von Maps, die die neuen Kursinformationen enthalten.
     *                    Jede Map sollte die Kurs-ID enthalten.
     * @return Eine `ResponseEntity` mit einer Map, die den Status der Aktualisierung angibt.
     */
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

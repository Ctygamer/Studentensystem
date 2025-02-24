package com.canama.studentsystem.controller;

import com.canama.studentsystem.Entity.Course;
import com.canama.studentsystem.Entity.Student;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Der `CourseController` dient als REST-Controller für die Verwaltung von Kursen im Studentensystem.
 * <p>
 * Er bietet Endpunkte zur Erstellung, Abfrage, Aktualisierung und Löschung von Kursen.
 * Zusätzlich können Studenten Kursen zugewiesen oder von diesen entfernt werden.
 * </p>
 * <p>
 * Die Annotationen `@RestController` und `@RequestMapping` legen die Basiskonfiguration für den Controller fest,
 * während `@CrossOrigin` den Zugriff von externen Domains erlaubt.
 * </p>
 */
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor // Automatischer Konstrucktor für finale Felder
public class CourseController {

    /**
     * Repository für den Zugriff auf Kursdaten.
     * Spring übernimmt die Instanziierung automatisch durch `@Autowired`.
     */
    private final CourseRepository courseRepository;

    /**
     * Repository für den Zugriff auf Studentendaten.
     */
    private final StudentRepository studentRepository;

    /**
     * Fügt einen neuen Kurs hinzu.
     *
     * @param course Der Kurs, der hinzugefügt werden soll.
     * @return Eine Nachricht, die den Erfolg der Operation bestätigt.
     */
    @PostMapping("/add")
    public String addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return "Course added successfully";
    }

    /**
     * Gibt eine Liste aller Kurse zurück.
     *
     * @return Eine Liste aller `Course`-Objekte.
     */
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Löscht einen Kurs anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Kurses.
     * @return Eine Nachricht, die den Status der Löschoperation angibt.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        Course course = courseRepository.findById(id).orElse(null);

        // Überprüfen, ob der Kurs existiert
        if (course == null) {
            return "Kurs wurde nicht gefunden.";
        }

        // Überprüfen, ob noch Studenten im Kurs sind
        if (course.getStudents() != null && !course.getStudents().isEmpty()) {
            return "Dieser Kurs kann nicht gelöscht werden, weil noch Studenten eingeschrieben sind.";
        }

        // Kurs löschen
        courseRepository.deleteById(id);

        return "Kurs erfolgreich gelöscht.";
    }




    /**
     * Fügt einen Studenten zu einem Kurs hinzu.
     *
     * @param courseId  Die ID des Kurses, zu dem der Student hinzugefügt werden soll.
     * @param studentId Die ID des Studenten, der hinzugefügt werden soll.
     * @return Eine Nachricht, ob der Student erfolgreich hinzugefügt wurde oder bereits Teil des Kurses ist.
     */
    //POSTMapping
    @PutMapping("/{courseId}/add-student/{studentId}")
    public String addStudentToCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        //  Sicherstellen, dass die Liste nicht null ist
        if (course.getStudents() == null) {
            course.setStudents(new ArrayList<>()); // Initialisiert die Liste
        }

        boolean alreadyExists = course.getStudents().stream()
                .anyMatch(s -> Objects.equals(s.getId(), student.getId()));

        if (!alreadyExists) {
            course.getStudents().add(student);
            courseRepository.save(course);
            return "Student added to course!";
        }

        return "Student is already in the course.";
    }

    /**
     * Aktualisiert die Kurszuordnung eines Studenten.
     * <p>
     * Entfernt den Studenten aus allen seinen bestehenden Kursen und weist ihn einem neuen Kurs zu.
     * </p>
     *
     * @param courseId  Die ID des neuen Kurses.
     * @param studentId Die ID des Studenten, dessen Kurs aktualisiert wird.
     * @return Eine `ResponseEntity`, die den Status der Aktualisierung angibt.
     */
    @PutMapping("/{courseId}/update-student/{studentId}")
    public ResponseEntity<?> updateStudentCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        Course newCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Entferne den Studenten aus alten Kursen
        student.getCourses().forEach(course -> course.getStudents().remove(student));
        student.getCourses().clear();

        // Füge den neuen Kurs hinzu
        student.getCourses().add(newCourse);
        newCourse.getStudents().add(student);

        studentRepository.save(student);
        courseRepository.save(newCourse);

        return ResponseEntity.ok("Student updated to new course!");
    }

}

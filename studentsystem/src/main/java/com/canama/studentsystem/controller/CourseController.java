package com.canama.studentsystem.controller;

    import com.canama.studentsystem.DTO.CourseDto;
    import com.canama.studentsystem.service.CourseService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import lombok.RequiredArgsConstructor;

    import java.util.List;
    /**
     * Controller für die Verwaltung von Kursen.
     */
    @RestController
    @RequestMapping("/course")
    @RequiredArgsConstructor
    public class CourseController {


        private final CourseService courseService;
        private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

        /**
         * Gibt alle Kurse zurück.
         *
         * @return eine Liste aller Kurse
         */
        @GetMapping
        public ResponseEntity<List<CourseDto>> getAllCourses() {
            logger.info("Rufe alle Kurse ab.");
            List<CourseDto> courses = courseService.getAllCourses();
            logger.info("Anzahl der abgerufenen Kurse: {}", courses.size());
            return ResponseEntity.ok(courses);
        }

        /**
         * Fügt einen neuen Kurs hinzu.
         *
         * @param courseDto der hinzuzufügende Kurs
         * @return der hinzugefügte Kurs
         */
        @PostMapping
        public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
            logger.info("Füge neuen Kurs hinzu: {}", courseDto);
            CourseDto savedCourse = courseService.addCourse(courseDto);
            logger.info("Kurs hinzugefügt: {}", savedCourse);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
        }

        /**
         * Löscht einen Kurs anhand der ID.
         * @param id die ID des zu löschenden Kurses
         * @return eine Antwort, die das Ergebnis der Löschung angibt
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
            logger.info("Lösche Kurs mit ID: {}", id);
            courseService.deleteCourseById(id);
            logger.info("Kurs mit ID: {} gelöscht.", id);
            return ResponseEntity.noContent().build();
        }

        /**
         * Fügt einen Studenten zu einem Kurs hinzu.
         *
         * @param courseId die ID des Kurses
         * @param studentId die ID des hinzuzufügenden Studenten
         * @return eine Antwort, die das Ergebnis der Operation angibt
         */
        @PutMapping("/{courseId}/add-student/{studentId}")
        public ResponseEntity<String> addStudentToCourse(@PathVariable Integer courseId,
                                                         @PathVariable Integer studentId) {
            logger.info("Füge Studenten mit ID: {} zu Kurs mit ID: {} hinzu.", studentId, courseId);
            try {
                String result = courseService.addStudentToCourse(courseId, studentId);
                logger.info("Student erfolgreich hinzugefügt: {}", result);
                return ResponseEntity.ok(result);
            } catch (Exception e) {
                logger.error("Fehler beim Hinzufügen von Studenten: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Fehler: " + e.getMessage());
            }
        }
    }
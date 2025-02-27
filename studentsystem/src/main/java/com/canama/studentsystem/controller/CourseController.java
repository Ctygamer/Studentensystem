package com.canama.studentsystem.controller;

    import com.canama.studentsystem.DTO.CourseDto;
    import com.canama.studentsystem.entity.Course;
    import com.canama.studentsystem.mapper.CourseMapper;
    import com.canama.studentsystem.repository.CourseRepository;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import lombok.RequiredArgsConstructor;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    /**
     * Controller für die Verwaltung von Kursen.
     */
    @RestController
    @RequestMapping("/course")
    @RequiredArgsConstructor
    public class CourseController {

        private final CourseRepository courseRepository;
        private final CourseMapper courseMapper;

        /**
         * Gibt alle Kurse zurück.
         *
         * @return eine Liste aller Kurse
         */
        @GetMapping
        public ResponseEntity<List<CourseDto>> getAllCourses() {
            List<Course> courses = courseRepository.findAll();
            List<CourseDto> courseDtos = courses.stream()
                    .map(courseMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(courseDtos);
        }

        /**
         * Fügt einen neuen Kurs hinzu.
         *
         * @param courseDto der hinzuzufügende Kurs
         * @return der hinzugefügte Kurs
         */
        @PostMapping("/add")
        public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
            Course course = courseMapper.toEntity(courseDto);
            Course savedCourse = courseRepository.save(course);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(courseMapper.toDto(savedCourse));
        }

        /**
         * Löscht einen Kurs anhand der ID.
         *
         * @param courseId die ID des zu löschenden Kurses
         * @return eine Antwort, die das Ergebnis der Löschung angibt
         */
        @DeleteMapping("/delete/{courseId}")
        public ResponseEntity<String> deleteCourse(@PathVariable Integer courseId) {
            Optional<Course> courseOpt = courseRepository.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kurs nicht gefunden");
            }
            Course course = courseOpt.get();
            if (!course.getStudents().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Kurs kann nicht gelöscht werden, da er eingeschriebene Studenten hat");
            }
            courseRepository.delete(course);
            return ResponseEntity.ok("Kurs erfolgreich gelöscht");
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
            try {
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Kurs nicht gefunden"));
                // Logik zum Suchen und Hinzufügen des Studenten hier
                return ResponseEntity.ok("Student zum Kurs hinzugefügt!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Fehler: " + e.getMessage());
            }
        }
    }
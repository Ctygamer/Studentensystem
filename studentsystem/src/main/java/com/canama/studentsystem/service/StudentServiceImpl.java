package com.canama.studentsystem.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.canama.studentsystem.Entity.Course;
import com.canama.studentsystem.Entity.Student;
import com.canama.studentsystem.repository.CourseRepository;
import com.canama.studentsystem.repository.StudentRepository;

/**
 * Implementierung des StudentService-Interfaces für die Verwaltung von Studenten- und Kursdaten.
 * Die Klasse nutzt Spring's Dependency Injection Framework und Transaktionsverwaltung.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    /**
     * Speichert einen Studenten in der Datenbank. Falls der Student bereits existiert
     * (überprüft durch die ID), werden seine Kurse aktualisiert.
     *
     * @param student Der zu speichernde Student.
     * @return Der gespeicherte Student.
     */
    @Override
    public Student saveStudent(Student student) {
        // Annahme: `getId()` ist durch ein entsprechendes Getter ersetzt, wenn der direkte Feldzugriff nicht möglich ist.
        if (student.getId() != 0) { // Prüft, ob eine ID vorhanden ist
            return updateStudentCourses(student);
        }
        return studentRepository.save(student);
    }

    /**
     * Ruft alle Studenten aus der Datenbank ab.
     *
     * @return Eine Liste von Studenten.
     */

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Löscht einen Studenten aus der Datenbank anhand seiner ID. Vor dem Löschen
     * werden die Beziehungen zu seinen Kursen entfernt.
     *
     * @param id Die ID des zu löschenden Studenten.
     * @throws RuntimeException Wenn der Student mit der angegebenen ID nicht existiert.
     */
    @Override
    public void deleteStudentById(Integer id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.getCourses().clear(); // Bereinigt die Beziehung
        studentRepository.save(student); // Aktualisiert den Studenten ohne Kurse
        studentRepository.deleteById(id); // Löscht den Studenten
    }

    /**
     * Ruft einen Studenten anhand seiner ID ab.
     *
     * @param id Die ID des gesuchten Studenten.
     * @return Der gefundene Student.
     * @throws RuntimeException Wenn der Student mit der angegebenen ID nicht existiert.
     */
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    /**
     * Aktualisiert die Kurse eines bestehenden Studenten. Es werden neue Kurse hinzugefügt
     * und nicht mehr vorhandene Kurse entfernt.
     *
     * @param student Der Student mit aktualisierten Kursinformationen.
     * @return Der Student mit aktualisierten Kursinformationen.
     * @throws RuntimeException Wenn der Student oder einer der Kurse nicht existiert.
     */
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

package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.entity.Student;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper zur Umwandlung zwischen Student-Entitäten und dem zugehörigen Datenübertragungsobjekt (DTO).
 *
 * Diese Klasse verwendet den {@code CourseMapper}, um die Kursdaten zu konvertieren.
 */
@Component
public class StudentMapper {

    private final CourseMapper courseMapper;

    /**
     * Konstruktor zum Injizieren des {@code CourseMapper}.
     *
     * @param courseMapper der Mapper zur Umwandlung von Kurs-Entitäten in DTOs und umgekehrt
     */
    public StudentMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    /**
     * Wandelt eine {@code Student}-Entität in ein {@code StudentDto} um.
     *
     * @param student die zu konvertierende Student-Entität
     * @return das entsprechende {@code StudentDto}-Objekt
     */
    public StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getAddress(),
                student.getCourses().stream()
                        .map(courseMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Wandelt ein {@code StudentDto} in eine {@code Student}-Entität um.
     *
     * Die Zuordnung der Kurse erfolgt separat, um Probleme mit dem Lazy Loading zu vermeiden.
     *
     * @param studentDto das zu konvertierende Datenübertragungsobjekt eines Studenten
     * @return die entsprechende {@code Student}-Entität
     */
    public Student toEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.id());
        student.setName(studentDto.name());
        student.setAddress(studentDto.address());
        // Note: Courses need to be handled separately to avoid Lazy Loading issues.
        return student;
    }
}
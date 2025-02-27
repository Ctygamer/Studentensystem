package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.entity.Course;
import org.springframework.stereotype.Component;

/**
 * Mapper zur Umwandlung zwischen Course-Entitäten und dem zugehörigen
 * Datenübertragungsobjekt (DTO).
 */
@Component
public class CourseMapper {

    /**
     * Wandelt eine {@code Course}-Entität in ein {@code CourseDto} um.
     *
     * @param course die zu konvertierende Course-Entität
     * @return das entsprechende {@code CourseDto}-Objekt
     */
    public CourseDto toDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getDescription()
        );
    }

    /**
     * Wandelt ein {@code CourseDto} in eine {@code Course}-Entität um.
     *
     * @param courseDto das zu konvertierende CourseDto-Objekt
     * @return die entsprechende {@code Course}-Entität
     */
    public Course toEntity(CourseDto courseDto) {
        Course course = new Course();
        course.setId(courseDto.id());
        course.setName(courseDto.name());
        course.setDescription(courseDto.description());
        return course;
    }
}
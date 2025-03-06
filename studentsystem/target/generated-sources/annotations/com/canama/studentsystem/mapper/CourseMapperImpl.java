package com.canama.studentsystem.mapper;

import com.canama.studentsystem.DTO.CourseDto;
import com.canama.studentsystem.entity.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T20:42:23+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;

        id = course.getId();
        name = course.getName();
        description = course.getDescription();

        CourseDto courseDto = new CourseDto( id, name, description );

        return courseDto;
    }

    @Override
    public Course toEntity(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.description( courseDto.description() );
        course.id( courseDto.id() );
        course.name( courseDto.name() );

        return course.build();
    }
}

package com.canama.studentsystem;

import com.canama.studentsystem.DTO.StudentDto;
import com.canama.studentsystem.entity.Student;
import com.canama.studentsystem.mapper.StudentMapper;
import com.canama.studentsystem.repository.StudentRepository;
import com.canama.studentsystem.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        student = new Student(1, "John Doe", "123 Street", List.of());
        studentDto = new StudentDto(1, "John Doe", "123 Street", List.of());
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toDto(student)).thenReturn(studentDto);

        List<StudentDto> result = studentService.getAllStudents();

        assertThat(result).hasSize(1);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void deleteStudentById_ShouldThrowException_WhenStudentNotFound() {
        when(studentRepository.existsById(1)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> studentService.deleteStudentById(1));
        assertThat(exception.getMessage()).isEqualTo("Student mit der Id: 1 nicht gefunden");
    }

    @Test
    void deleteStudentById_ShouldDelete_WhenStudentExists() {
        when(studentRepository.existsById(1)).thenReturn(true);

        studentService.deleteStudentById(1);

        verify(studentRepository, times(1)).deleteById(1);
    }
}

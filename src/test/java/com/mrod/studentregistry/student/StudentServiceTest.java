package com.mrod.studentregistry.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Test
    void testAddStudent_emailAlreadyExists() {
        String email = "johndoe@yahoo.com";
        Student student = new Student("johndoe", email, LocalDate.of(2000, Month.APRIL, 21));
        StudentService service = new StudentService(studentRepository);

        Mockito.when(studentRepository.findByEmail(Mockito.eq(email)))
                .thenReturn(Optional.of(new Student("anotherStudent", email, LocalDate.of(1987, Month.JANUARY, 7))));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.addStudent(student));
        assertEquals("Email taken", exception.getMessage());
    }

    @Test
    void testAddStudent_success() {
        String email = "johndoe@yahoo.com";
        Student student = new Student("johndoe", email, LocalDate.of(2000, Month.APRIL, 21));
        StudentService service = new StudentService(studentRepository);

        Mockito.when(studentRepository.findByEmail(Mockito.eq(email)))
                .thenReturn(Optional.empty());

        service.addStudent(student);
    }
}
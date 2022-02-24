package com.mrod.studentregistry.student.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrod.studentregistry.exceptions.StatusCode;
import com.mrod.studentregistry.exceptions.StudentRegistryException;
import com.mrod.studentregistry.model.Student;
import com.mrod.studentregistry.repository.StudentRepository;
import com.mrod.studentregistry.service.StudentService;

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

        StudentRegistryException exception = assertThrows(StudentRegistryException.class, () -> service.addStudent(student));
        assertEquals(StatusCode.STUDENT_EMAIL_TAKEN, exception.getStatusCode());
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
package com.mrod.school.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mrod.school.entities.Student;
import com.mrod.school.exceptions.SchoolException;
import com.mrod.school.exceptions.StatusCode;
import com.mrod.school.repository.CourseRepository;
import com.mrod.school.repository.StudentRepository;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new SchoolException(StatusCode.STUDENT_NOT_FOUND));
    }

    public Student addStudent(Student student) {
        validateEmail(student.getEmail());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
    }

    public void updateStudent(Long id, Student newStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new SchoolException(StatusCode.STUDENT_NOT_FOUND));

        if (!Objects.equals(newStudent.getEmail(), student.getEmail())) {
            validateEmail(newStudent.getEmail());
            student.setEmail(newStudent.getEmail());
        }

        if (!Objects.equals(newStudent.getName(), student.getName())) {
            student.setName(newStudent.getName());
        }

        if (!Objects.equals(newStudent.getDob(), student.getDob())) {
            student.setDob(newStudent.getDob());
        }
        studentRepository.save(student);
    }

    private void validateEmail(String email) {
        Optional<Student> result = studentRepository.findByEmail(email);
        if (result.isPresent()) {
            throw new SchoolException(StatusCode.STUDENT_EMAIL_TAKEN);
        }
    }
}

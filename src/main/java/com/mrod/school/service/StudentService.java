//
//                              ASSIA, Inc.
//                               Copyright
//                     Confidential and Proprietary
//                         ALL RIGHTS RESERVED.
//
//      http://www.assia-inc.com, +1.650.654.3400
//
//      This software is provided under license and may be used, 
//      copied, distributed (with or without modification), or made 
//      available to the public, only in accordance with the terms
//      of such license.
//

package com.mrod.school.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mrod.school.entities.Student;
import com.mrod.school.exceptions.SchoolException;
import com.mrod.school.exceptions.StatusCode;
import com.mrod.school.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudent(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SchoolException(StatusCode.STUDENT_NOT_FOUND));
    }

    public Student addStudent(Student student) {
        validateEmail(student.getEmail());
        return repository.save(student);
    }

    public void deleteStudent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public void updateStudent(Long id, Student newStudent) {
        Student student = repository.findById(id)
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
        repository.save(student);
    }

    private void validateEmail(String email) {
        Optional<Student> result = repository.findByEmail(email);
        if (result.isPresent()) {
            throw new SchoolException(StatusCode.STUDENT_EMAIL_TAKEN);
        }
    }
}

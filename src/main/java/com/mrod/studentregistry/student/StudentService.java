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

package com.mrod.studentregistry.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mrod.studentregistry.exceptions.StatusCode;
import com.mrod.studentregistry.exceptions.StudentRegistryException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentRegistryException(StatusCode.STUDENT_NOT_FOUND));
    }

    @Transactional
    public void addStudent(Student student) {
        validateEmail(student.getEmail());
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
    }

    @Transactional
    public void updateStudent(Long id, Student newStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentRegistryException(StatusCode.STUDENT_NOT_FOUND));

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
            throw new StudentRegistryException(StatusCode.STUDENT_EMAIL_TAKEN);
        }
    }
}

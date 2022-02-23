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

package com.example.demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
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
                .orElseThrow(() -> new IllegalStateException("No student with that id"));

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
            throw new IllegalStateException("Email taken");
        }
    }
}

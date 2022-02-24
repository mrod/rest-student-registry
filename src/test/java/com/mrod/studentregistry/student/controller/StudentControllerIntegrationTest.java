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

package com.mrod.studentregistry.student.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.mrod.studentregistry.controller.StudentController;
import com.mrod.studentregistry.exceptions.StatusCode;
import com.mrod.studentregistry.exceptions.StudentRegistryException;
import com.mrod.studentregistry.model.Student;
import com.mrod.studentregistry.service.StudentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerIntegrationTest {

    @MockBean
    StudentService studentService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(
                new Student("mariam", "mariam@gmail.com", LocalDate.of(2000, Month.JANUARY, 7)),
                new Student("alex", "alex@gmail.com", LocalDate.of(1954, Month.SEPTEMBER, 12))
        );
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("mariam")))
                .andExpect(jsonPath("$[0].email", Matchers.is("mariam@gmail.com")))
                .andExpect(jsonPath("$[0].dob", Matchers.is("2000-01-07")))
                .andExpect(jsonPath("$[0].age", Matchers.is(22)))
                .andExpect(jsonPath("$[1].name", Matchers.is("alex")))
                .andExpect(jsonPath("$[1].email", Matchers.is("alex@gmail.com")))
                .andExpect(jsonPath("$[1].dob", Matchers.is("1954-09-12")))
                .andExpect(jsonPath("$[1].age", Matchers.is(67)))
        ;
    }

    @Test
    public void testGetStudent_notFound() throws Exception {
        when(studentService.getStudent(Mockito.anyLong())).thenThrow(new StudentRegistryException(StatusCode.STUDENT_NOT_FOUND));

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isNotFound())
        ;
    }
}

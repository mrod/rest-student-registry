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
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mrod.school.entities.Course;
import com.mrod.school.entities.Student;
import com.mrod.school.exceptions.SchoolException;
import com.mrod.school.exceptions.StatusCode;
import com.mrod.school.repository.CourseRepository;
import com.mrod.school.repository.StudentRepository;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new SchoolException(StatusCode.COURSE_NOT_FOUND));
    }

    public Course addCourse(Course course) {
        // TODO validate no other course with the same name
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Optional<Course> byId = courseRepository.findById(id);
        if (!byId.isPresent()) {
            return;
        }
        Course course = byId.get();
        course.getStudents().forEach(s -> s.removeCourse(course));
        courseRepository.deleteById(id);
    }

    public Course registerStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new SchoolException(StatusCode.COURSE_NOT_FOUND));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new SchoolException(StatusCode.STUDENT_NOT_FOUND));

        if (course.getStudents().contains(student)) {
            logger.info("Student '{}' is already registered in course '{}'", student.getId(), course.getId());
            return course;
        }
        course.addStudent(student);
        // Course is NOT the owning side of the N:N relationship, so we have to update both entities
        student.addCourse(course);
        courseRepository.save(course);
        return course;
    }
}

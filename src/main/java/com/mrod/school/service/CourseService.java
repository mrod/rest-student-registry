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

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mrod.school.entities.Course;
import com.mrod.school.exceptions.SchoolException;
import com.mrod.school.exceptions.StatusCode;
import com.mrod.school.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course getCourse(Long id) {
        return repository.findById(id).orElseThrow(() -> new SchoolException(StatusCode.COURSE_NOT_FOUND));
    }

    @Transactional
    public void addCourse(Course course) {
        // TODO validate no other course with the same name
        repository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Optional<Course> byId = repository.findById(id);
        if (!byId.isPresent()) {
            return;
        }
        Course course = byId.get();
        course.getStudents().forEach(s -> s.removeCourse(course));
        repository.deleteById(id);
    }


}

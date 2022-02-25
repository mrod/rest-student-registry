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

package com.mrod.school.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrod.school.entities.Course;
import com.mrod.school.service.CourseService;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @GetMapping(path = "{courseId}")
    public Course getCourse(@PathVariable("courseId") Long id) {
        return service.getCourse(id);
    }

    @PostMapping
    public void addCourse(@RequestBody Course course) {
        service.addCourse(course);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long id) {
        service.deleteCourse(id);
    }


}

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
    public Course addCourse(@RequestBody Course course) {
        return service.addCourse(course);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long id) {
        service.deleteCourse(id);
    }

    @PutMapping(path = "{courseId}/student/{studentId}")
    public Course registerStudent(@PathVariable("courseId") Long courseId,
                                  @PathVariable("studentId") Long studentId) {
        return service.registerStudent(courseId, studentId);
    }

}

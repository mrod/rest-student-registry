package com.mrod.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrod.school.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

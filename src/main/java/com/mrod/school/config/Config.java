package com.mrod.school.config;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mrod.school.entities.Course;
import com.mrod.school.entities.Student;
import com.mrod.school.repository.StudentRepository;

@Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Course algebra = new Course("Algebra");
            Course english = new Course("English");

            Student mariam = new Student("mariam", "mariam@gmail.com", LocalDate.of(2000, Month.JANUARY, 7));
            Student alex = new Student("alex", "alex@gmail.com", LocalDate.of(1954, Month.SEPTEMBER, 12));

            mariam.setCourses(new HashSet<>(Arrays.asList(algebra, english)));
            alex.setCourses(new HashSet<>(Arrays.asList(english)));

            studentRepository.saveAll(Arrays.asList(mariam, alex));

        };
    }
}


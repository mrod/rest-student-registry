package com.mrod.studentregistry.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student("mariam", "mariam@gmail.com", LocalDate.of(2000, Month.JANUARY, 7));
            Student alex = new Student("alex", "alex@gmail.com", LocalDate.of(1954, Month.SEPTEMBER, 12));
            repository.saveAll(Arrays.asList(mariam, alex));
        };
    }
}


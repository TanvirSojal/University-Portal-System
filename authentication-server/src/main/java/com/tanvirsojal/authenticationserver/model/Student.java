package com.tanvirsojal.authenticationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})

public class Student {
    private String id;
    private String name;
    private int batch;
    private Program program;
    private LocalDate admissionDate;
    private int creditsCompleted;
    private double cgpa;
    private List<Course> registeredCourseList;
    private List<Grade> gradedCourseList;
    @Email
    private String email;
}
package com.tanvirsojal.studentserver.model;

import lombok.*;

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

    public void calculateCGPA(){
        double totalGradePoints = 0;
        double totalCredits = 0;
        int creditsCompleted = 0;

        for (Grade g : gradedCourseList){
            totalGradePoints += (g.getGrade() * g.getCourse().getCreditHours());
            totalCredits += g.getCourse().getCreditHours();
            if (g.getGrade() > 0)
                creditsCompleted += g.getCourse().getCreditHours();

        }
        this.cgpa = totalGradePoints / totalCredits;
        this.creditsCompleted = (int)creditsCompleted;
    }
}

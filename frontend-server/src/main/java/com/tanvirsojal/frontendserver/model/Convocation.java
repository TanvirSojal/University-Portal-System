package com.tanvirsojal.frontendserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Convocation {
    private int id;
    private Student student;
    private String paymentStatus;
    private String confirmationStatus;

    public String getStudentId(){
        return student.getId();
    }

    public String getStudentName(){
        return student.getName();
    }

    public double getStudentCGPA(){
        return student.getCgpa();
    }

    public int getStudentCreditsCompleted(){
        return student.getCreditsCompleted();
    }

}

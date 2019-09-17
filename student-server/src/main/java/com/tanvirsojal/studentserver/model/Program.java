package com.tanvirsojal.studentserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    private int id;
    private String title;
    private String coordinatorId;
    private double minCGPAForGraduation;
    private int minCreditForGraduation;
    private List<Course> courseList;
}

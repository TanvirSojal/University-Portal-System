package com.tanvirsojal.authenticationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String code;
    private String title;
    private double creditHours;
    private int programId;
    private String programTitle;
}

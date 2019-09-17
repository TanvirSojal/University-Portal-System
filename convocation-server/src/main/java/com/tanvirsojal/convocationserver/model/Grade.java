package com.tanvirsojal.convocationserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private Course course;
    private double mark;
    private double grade;
}

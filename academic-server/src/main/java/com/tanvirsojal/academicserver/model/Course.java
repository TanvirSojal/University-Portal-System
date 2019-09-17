package com.tanvirsojal.academicserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"code"})
public class Course {
    @Id
    private String code;
    private String title;
    private double creditHours;
    private int programId;
    private String programTitle;
}
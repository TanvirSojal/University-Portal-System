package com.tanvirsojal.frontendserver.model;

import com.tanvirsojal.frontendserver.service.UserService;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String code;
    private String title;
    private double creditHours;
    private int programId;
    private String programTitle;

    @Override
    public String toString(){
        return code + " : " + title + " : " + creditHours + " credit(s)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }
}
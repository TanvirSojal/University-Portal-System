package com.tanvirsojal.frontendserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Program {
    private int id;
    private String title;
    private String coordinatorId;
    private double minCGPAForGraduation;
    private int minCreditForGraduation;
    private List<Course> courseList;

    @Override
    public String toString(){
        return this.title;
    }

    public String getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(String coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMinCGPAForGraduation() {
        return minCGPAForGraduation;
    }

    public void setMinCGPAForGraduation(double minCGPAForGraduation) {
        this.minCGPAForGraduation = minCGPAForGraduation;
    }

    public int getCourseCount(){
        return this.courseList.size();
    }

    public int getMinCreditForGraduation() {
        return minCreditForGraduation;
    }

    public void setMinCreditForGraduation(int minCreditForGraduation) {
        this.minCreditForGraduation = minCreditForGraduation;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}

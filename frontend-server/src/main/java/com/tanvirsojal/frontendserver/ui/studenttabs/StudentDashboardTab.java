package com.tanvirsojal.frontendserver.ui.studenttabs;

import com.tanvirsojal.frontendserver.model.Course;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.servlet.http.HttpSession;

public class StudentDashboardTab extends VerticalLayout {
    private Grid<Course> courseGrid;
    private Grid<Student> studentGrid;

    private Student student;
    public StudentDashboardTab(UserService userService, HttpSession httpSession) {
        courseGrid = new Grid<>(Course.class);
        studentGrid = new Grid<>(Student.class);

        LoginToken loginToken = (LoginToken) httpSession.getAttribute("user");

        if (loginToken != null){
            student = userService.getStudent(loginToken.getUsername());

        }


        H4 courseLabel = new H4("Courses you registered this semester:");
        courseLabel.getStyle()
                .set("margin-left", "400px");

        courseGrid.setWidth("1000px");
        courseGrid.setHeight("300px");
        courseGrid.getStyle()
                .set("margin-left", "400px");

        courseGrid.removeAllColumns();
        courseGrid
                .addColumn(Course::getCode)
                .setHeader("Course ID");
        courseGrid
                .addColumn(Course::getTitle)
                .setHeader("Title");
        courseGrid
                .addColumn(Course::getCreditHours)
                .setHeader("Credit Hours");

        courseGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);


        H4 studentLabel = new H4("Your Academic Information");
        studentLabel.getStyle()
                .set("margin-left", "400px");

        studentGrid.setWidth("1000px");
        studentGrid.setHeight("150px");
        studentGrid.getStyle()
                .set("margin-left", "400px");

        studentGrid.removeAllColumns();

        studentGrid
                .addColumn(Student::getId)
                .setHeader("Student ID");

        studentGrid
                .addColumn(Student::getName)
                .setHeader("Name");

        studentGrid
                .addColumn(Student::getBatch)
                .setHeader("Batch");

        studentGrid
                .addColumn(Student::getCgpa)
                .setHeader("CGPA");

        studentGrid
                .addColumn(Student::getCreditsCompleted)
                .setHeader("Credits Completed");

        if (student != null){
            courseGrid.setItems(student.getRegisteredCourseList());
            studentGrid.setItems(student);
        }

        add(courseLabel, courseGrid, studentLabel, studentGrid);
    }
}

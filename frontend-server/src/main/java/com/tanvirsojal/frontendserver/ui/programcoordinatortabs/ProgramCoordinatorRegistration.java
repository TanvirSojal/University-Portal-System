package com.tanvirsojal.frontendserver.ui.programcoordinatortabs;

import com.tanvirsojal.frontendserver.model.Course;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.model.Program;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProgramCoordinatorRegistration extends VerticalLayout {
    private Grid<Student> studentGrid;
    private Grid<Course> courseGrid;
    private ComboBox<Course> courseComboBox;

    public ProgramCoordinatorRegistration(UserService userService, HttpSession httpSession) {
        studentGrid = new Grid<>(Student.class);
        courseGrid = new Grid<>(Course.class);
        courseComboBox = new ComboBox<>();


        courseGrid.removeAllColumns();

        studentGrid.setWidth("1400px");
        studentGrid.setHeight("300px");
        studentGrid.getStyle()
                .set("margin-left", "200px");

        courseGrid.setWidth("900px");
        courseGrid.setHeight("300px");
//        courseGrid.getStyle()
//                .set("margin-left", "200px");



        H4 gridLabel = new H4("Students under your co-ordination");
        gridLabel.getStyle()
                .set("margin-left", "200px");

        H4 registeredCourseLabel = new H4();
        registeredCourseLabel.getStyle()
                .set("margin-left", "200px");

        List<Program> programList = userService.getPrograms();
        Program program = new Program();
        LoginToken loginToken = (LoginToken) httpSession.getAttribute("user");


        for (Program p: programList){
            if (p.getCoordinatorId().equals(loginToken.getUsername())){
                program = p;
                break;
            }
        }


//        System.out.println("Coordinator of : " +
//                program);
        List<Student> studentList = new ArrayList<>();

        List<Student> allStudents = userService.getStudents();
        for (Student s :  allStudents){
            if (s.getProgram().getId() == program.getId())
                studentList.add(s);
        }




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

        studentGrid
                .addColumn(Student::getEmail)
                .setHeader("Email");

//        studentGrid
//                .addComponentColumn(this::getRegisterButton)
//                .setFlexGrow(1)
//                .setWidth("100px");

        studentGrid.setItems(studentList);

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


        studentGrid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(e -> {
                registeredCourseLabel.setText("ID: " + e.getId() + "   -   Name: " + e.getName() + "   -   Credit(s) Completed: " + e.getCreditsCompleted());
                courseGrid.setItems(e.getRegisteredCourseList());
            });
        });

        List<Course> allCourses = userService.getCourses();
        List<Course> courseList = new ArrayList<>();

        for (Course c : allCourses){
            if (c.getProgramId() == program.getId())
                courseList.add(c);
        }

        courseComboBox.setItems(courseList);
        courseComboBox.setValue(courseList.get(0));



        H4 selectCourseLabel = new H4("Select Course to Register");

        Button registerButton = new Button("Register", VaadinIcon.CHECK_SQUARE.create());
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancel", VaadinIcon.CLIPBOARD_CROSS.create());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(registerButton, cancelButton);

        cancelButton.addClickListener(event -> {
            courseComboBox.setValue(courseList.get(0));
            courseGrid.setItems(new ArrayList<>());
            registeredCourseLabel.setText("");
        });

        registerButton.addClickListener(event -> {
            Set<Student> selectedStudents = studentGrid.getSelectedItems();
            if (selectedStudents.size() == 0){
                Notification.show("Select a student first!");
            }
            else{
                Student student = userService.getStudent(selectedStudents.iterator().next().getId());

//                student.getRegisteredCourseList().forEach(System.out::println);

                Course course = courseComboBox.getValue();
                int index = student.getRegisteredCourseList().indexOf(course);
//                Notification.show(index + "");
                if (index < 0){
                    student.getRegisteredCourseList().add(course);
                    try{
                        userService.updateStudent(student);
                        courseGrid.setItems(student.getRegisteredCourseList());
                        Notification.show("Successfully registered " + student.getId() + " into " + course.getCode());
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    Notification.show(student.getId() + " is already registered into " + course.getCode());
                }
            }
        });

        courseComboBox.setWidth("400px");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(selectCourseLabel, courseComboBox, buttonLayout);
        verticalLayout.setWidth("400px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(courseGrid, verticalLayout);

        horizontalLayout.getStyle()
                .set("margin-left", "200px");
        add(gridLabel, studentGrid, registeredCourseLabel, horizontalLayout);
    }

    private Component getRegisterButton(Student student) {
        Button button = new Button("Register");
        button.getElement().setProperty("title", "Register Courses");
        button.setIcon(VaadinIcon.DIPLOMA.create());
        button.addClickListener(event -> {

        });
        return button;
    }
}

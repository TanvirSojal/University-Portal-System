package com.tanvirsojal.frontendserver.ui.examinationofficertabs;

import com.tanvirsojal.frontendserver.model.Course;
import com.tanvirsojal.frontendserver.model.Grade;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

import static com.vaadin.flow.component.icon.VaadinIcon.CHECK_SQUARE;
import static com.vaadin.flow.component.icon.VaadinIcon.MINUS_SQUARE_O;

public class ExaminationOfficerGradingTab extends VerticalLayout {
    private Grid<Student> studentGrid;

    public ExaminationOfficerGradingTab(UserService userService) {
        studentGrid = new Grid<>(Student.class);

        H4 gradeLabel = new H4("Select student to grade");

        studentGrid.removeAllColumns();
        studentGrid
                .addColumn(Student::getId)
                .setWidth("150px")
                .setHeader("Student ID");

        studentGrid
                .addColumn(Student::getName)
                .setWidth("300px")
                .setHeader("Name");

        studentGrid
                .addColumn(Student::getProgram)
                .setWidth("120px")
                .setHeader("Program");

        studentGrid
                .addColumn(Student::getBatch)
                .setWidth("60px")
                .setHeader("Batch");

        studentGrid
                .addColumn(Student::getCgpa)
                .setWidth("60px")
                .setHeader("CGPA");

        studentGrid.setItems(userService.getStudents());
        studentGrid.setWidth("1000px");

        studentGrid.getStyle()
                .set("margin-left", "400px");
        gradeLabel.getStyle()
                .set("margin-left", "400px");


        studentGrid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(item -> {
                Dialog dialog = new Dialog();
                Student student = item;
                H4 title = new H4("Grade : " + student.getId() + " (" + student.getName() + ")");

                ComboBox <Course> courseCombobox = new ComboBox<>("Courses Not Graded");
                List<Course> registeredCourseList = student.getRegisteredCourseList();
                courseCombobox.setItems(registeredCourseList);
                if (registeredCourseList.size() > 0)
                    courseCombobox.setValue(registeredCourseList.get(0));



                TextField scoreField = new TextField("Score"); scoreField.setRequired(true);
                TextField gradeField = new TextField("Grade Point"); gradeField.setRequired(true);

                Label statusLabel = new Label("");

                courseCombobox.addFocusListener(e -> statusLabel.setText(""));
                scoreField.addFocusListener(e -> statusLabel.setText(""));
                gradeField.addFocusListener(e -> statusLabel.setText(""));

                Button submitButton = new Button("Confirm Grade", CHECK_SQUARE.create());
                submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                Button closeButton = new Button("Cancel", MINUS_SQUARE_O.create());
                closeButton.setWidth("200px");
                closeButton.getStyle()
                        .set("color", "white")
                        .set("background", "red");

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.add(submitButton, closeButton);

                FormLayout formLayout = new FormLayout();
                formLayout.add(courseCombobox, scoreField, gradeField, statusLabel);

                submitButton.addClickListener(e -> {
                    if (!NumberUtils.isCreatable(scoreField.getValue()) || !NumberUtils.isCreatable(gradeField.getValue())){
                        statusLabel.setText("Score and Grade must be decimal number!");
                    }
                    else{
                        double score = Double.parseDouble(scoreField.getValue());
                        double grade = Double.parseDouble(gradeField.getValue());

                        if (score > 100 || grade > 4.0){
                            statusLabel.setText("Score should be within [0.0, 100.0] and Grade should be within [0.0, 4.0]");
                        }

                        else{
                            if (courseCombobox.getValue() == null){
                                statusLabel.setText("You must select a course first to grade!");
                            }
                            else{
                                Course course = courseCombobox.getValue();
                                Grade newGrade = new Grade(course, score, grade);

                                student.getRegisteredCourseList().remove(course);
                                student.getGradedCourseList().add(newGrade);

                                student.calculateCGPA();

                                userService.updateStudent(student);

                                courseCombobox.setItems(student.getRegisteredCourseList());
                                if (student.getRegisteredCourseList().size() > 0)
                                    courseCombobox.setValue(student.getRegisteredCourseList().get(0));

                                System.out.println("Came here!");
                                studentGrid.setItems(userService.getStudents());

                                dialog.close();
                            }
                        }
                    }
                });

                closeButton.addClickListener(e -> {
                    dialog.close();
                });

                VerticalLayout verticalLayout = new VerticalLayout();
                verticalLayout.add(title, formLayout, horizontalLayout);

                dialog.setWidth("1000px");
                dialog.add(verticalLayout);
                dialog.setCloseOnOutsideClick(false);
                dialog.open();
                dialog.open();
            });
        });


        add(gradeLabel, studentGrid);
    }
}

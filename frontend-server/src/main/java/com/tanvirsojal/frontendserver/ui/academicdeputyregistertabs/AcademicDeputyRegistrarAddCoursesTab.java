package com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs;

import com.tanvirsojal.frontendserver.model.Course;
import com.tanvirsojal.frontendserver.model.Program;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import java.util.List;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class AcademicDeputyRegistrarAddCoursesTab extends VerticalLayout {
    private Grid<Course> courseGrid;
    private Binder<Course> courseBinder;

    public AcademicDeputyRegistrarAddCoursesTab(UserService userService) {
        courseBinder = new Binder<>();
        courseGrid = new Grid<>(Course.class);


        courseGrid.setWidth("1000px");
        courseGrid.getStyle()
                .set("margin-left", "400px");

        Button addCourseButton = new Button("Add Course", PLUS.create());
        addCourseButton.getStyle()
                .set("margin-left", "400px");


        courseGrid.removeAllColumns();
        courseGrid
                .addColumn(Course::getCode)
                .setWidth("100px")
                .setHeader("Course Code");

        courseGrid
                .addColumn(Course::getTitle)
                .setWidth("150px")
                .setHeader("Course Title");

        courseGrid
                .addColumn(Course::getProgramTitle)
                .setWidth("150px")
                .setHeader("Offered In");

        courseGrid
                .addColumn(Course::getCreditHours)
                .setWidth("100px")
                .setHeader("Credit Hours");

        courseGrid.setItems(userService.getCourses());

        addCourseButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            H4 title = new H4("Add New Course");
            title.getStyle().set("text-align", "center");

            Button closeButton = new Button("Cancel", MINUS_SQUARE_O.create());
            closeButton.setWidth("200px");
            closeButton.getStyle()
                    .set("color", "white")
                    .set("background", "red");

            Button submitButton = new Button("Create Course", CHECK_SQUARE.create());
            submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            TextField idField = new TextField("Course Code", "must be unique");
            TextField nameField = new TextField("Course Title", "must be a string");
            TextField creditHoursField = new TextField("Credit Hours", "i.e. 3.0");
//            TextField programIdField = new TextField("ID of Program It will be offered in", "i.e. 1 (CSE), 2 (BBA)");
            ComboBox<Program> programComboBox = new ComboBox<>("Program");
            List<Program> programList = userService.getPrograms();
            programComboBox.setItems(programList);
            programComboBox.setValue(programList.get(0));


            FormLayout formLayout = new FormLayout();
            formLayout.add(idField, nameField, creditHoursField, programComboBox);

            courseBinder
                    .forField(idField)
                    .asRequired()
                    .bind(Course::getCode, Course::setCode);

            courseBinder
                    .forField(nameField)
                    .asRequired()
                    .bind(Course::getTitle, Course::setTitle);

            courseBinder
                    .forField(creditHoursField)
                    .asRequired()
                    .withConverter(new StringToDoubleConverter("Must be decimal number"))
                    .bind(Course::getCreditHours, Course::setCreditHours);

//            courseBinder
//                    .forField(programIdField)
//                    .asRequired()
//                    .withConverter(new StringToIntegerConverter("Must be integer ID"))
//                    .bind(Course::getProgramId, Course::setProgramId);

            submitButton.addClickListener(e -> {
                Course course = new Course();

                try{
                    courseBinder.writeBean(course);
                    Program program = programComboBox.getValue();
                    course.setProgramId(program.getId());
                    course.setProgramTitle(program.getTitle());

                    userService.saveCourse(course);
                    Notification.show("Saved " + course.getTitle());
                    courseGrid.setItems(userService.getCourses());

                    dialog.close();

                } catch (ValidationException ex){
                    ex.printStackTrace();
                }
            });
            closeButton.addClickListener(e -> dialog.close());

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(submitButton, closeButton);

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.add(title, formLayout, horizontalLayout);

            dialog.setWidth("1000px");
            dialog.add(verticalLayout);
            dialog.setCloseOnOutsideClick(false);
            dialog.open();
        });

        add(addCourseButton, courseGrid);

    }
}

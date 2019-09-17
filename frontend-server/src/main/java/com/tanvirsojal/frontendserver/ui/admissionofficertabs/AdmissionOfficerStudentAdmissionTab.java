package com.tanvirsojal.frontendserver.ui.admissionofficertabs;

import com.tanvirsojal.frontendserver.model.Credential;
import com.tanvirsojal.frontendserver.model.Program;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class AdmissionOfficerStudentAdmissionTab extends VerticalLayout {

    private Grid<Student> studentGrid;
    private Binder<Student> studentBinder;

    public AdmissionOfficerStudentAdmissionTab(UserService userService, AuthenticationService authenticationService) {
        super();


        //Label label = new Label("Hello World!");
        this.studentBinder = new Binder<>();
        studentGrid = new Grid<>(Student.class);

        Button addStudentButton = new Button("Add Student", PLUS.create());

        addStudentButton.getStyle()
                .set("margin-left", "400px");



        // Setting up grid columns
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

        studentGrid
                .addColumn(Student::getAdmissionDate)
                .setWidth("100px")
                .setHeader("Admission Date");

        studentGrid.setItems(userService.getStudents());
        studentGrid.setWidth("1000px");
        studentGrid.getStyle()
                .set("margin-left", "400px");


        addStudentButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            H4 title = new H4("Admit New Student");
            title.getStyle().set("text-align", "center");

            Button closeButton = new Button("Cancel", MINUS_SQUARE_O.create());
            closeButton.setWidth("200px");
            closeButton.getStyle()
                    .set("color", "white")
                    .set("background", "red");


            Button admitButton = new Button("Confirm Admission", CHECK_SQUARE.create());
            admitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            VerticalLayout verticalLayout = new VerticalLayout();


            TextField idField = new TextField("Student ID", "6 digit ID");
            TextField nameField = new TextField("Name", "Student Name");
            TextField batchField = new TextField("Batch", "Batch");
//            TextField programField = new TextField("Program ID", "i.e 1 (CSE), 2 (BBA)");
            ComboBox<Program> programComboBox = new ComboBox<>("Program");
            List<Program> programList = userService.getPrograms();
            programComboBox.setItems(programList);
            programComboBox.setValue(programList.get(0));
            programComboBox.setRequired(true);

            EmailField emailField = new EmailField("Email", "Student Email");
            PasswordField passwordField = new PasswordField("Password", "use a strong password");
            PasswordField pconfirmPasswordField = new PasswordField("Confirm Password", "passwords must match");
            TextField creditField = new TextField("Credits Completed", "In case of credit transfer, put proper credit number");


            // setting up binder
            studentBinder
                    .forField(idField)
                    .asRequired()
                    .withValidator(id -> id.length() == 6, "ID must be 6 digit number")
                    .bind(Student::getId, Student::setId);

            studentBinder
                    .forField(nameField)
                    .asRequired()
                    .bind(Student::getName, Student::setName);

            studentBinder
                    .forField(batchField)
                    .asRequired()
                    .withConverter(new StringToIntegerConverter(("Batch must be an integer")))
                    .bind(Student::getBatch, Student::setBatch);

            studentBinder
                    .forField(emailField)
                    .asRequired()
                    .bind(Student::getEmail, Student::setEmail);

            studentBinder
                    .forField(creditField)
                    .asRequired()
                    .withConverter(new StringToIntegerConverter("credits must be integer"))
                    .bind(Student::getCreditsCompleted, Student::setCreditsCompleted);



            FormLayout formLayout = new FormLayout();
            formLayout.add(idField, nameField, batchField, programComboBox, creditField, emailField, passwordField, pconfirmPasswordField);

            admitButton.addClickListener(e -> {
                if (!passwordField.getValue().equals(pconfirmPasswordField.getValue())){
                    Notification.show("Passwords did not match, try again!");
                }

                else{
                    Student student = new Student();
                    try{
                        studentBinder.writeBean(student);

                        Program program = programComboBox.getValue();
                        student.setProgram(program);
                        student.setAdmissionDate(LocalDate.now());
                        student.setRegisteredCourseList(new ArrayList<>());
                        student.setGradedCourseList(new ArrayList<>());

                        Credential credential = new Credential(student.getId(), passwordField.getValue(), "student");
                        authenticationService.register(credential);

                        userService.saveStudent(student);
                        Notification.show("Saved " + student.getName());
                        studentGrid.setItems(userService.getStudents());

                        dialog.close();

                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });

            closeButton.addClickListener(e -> {
                dialog.close();
            });


//            closeButton.setWidth("600px");
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(admitButton, closeButton);

            verticalLayout.add(title, formLayout, horizontalLayout);

            dialog.setWidth("1000px");
            dialog.add(verticalLayout);
            dialog.setCloseOnOutsideClick(false);
            dialog.open();
        });

        add(addStudentButton, studentGrid);
    }
}

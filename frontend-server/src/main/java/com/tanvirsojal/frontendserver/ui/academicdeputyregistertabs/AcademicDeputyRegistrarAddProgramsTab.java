package com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs;

import com.tanvirsojal.frontendserver.model.Program;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import java.util.ArrayList;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class AcademicDeputyRegistrarAddProgramsTab extends VerticalLayout {
    private Grid<Program> programGrid;
    private Binder<Program> programBinder;

    public AcademicDeputyRegistrarAddProgramsTab(UserService userService) {
        programBinder = new Binder<>();
        programGrid = new Grid<>(Program.class);

        Button addProgramButton = new Button("Add Program", PLUS.create());

        addProgramButton.getStyle()
                .set("margin-left", "200px");

        programGrid.removeAllColumns();
        programGrid.setWidth("1400px");
        programGrid.getStyle()
                .set("margin-left", "200px");

        programGrid
                .addColumn(Program::getId)
                .setWidth("60px")
                .setHeader("Program ID");

        programGrid
                .addColumn(Program::getTitle)
                .setWidth("180px")
                .setHeader("Title");

        programGrid
                .addColumn(Program::getCourseCount)
                .setWidth("60px")
                .setHeader("Courses Offered");

        programGrid
                .addColumn(Program::getCoordinatorId)
                .setWidth("60px")
                .setHeader("Coordinator Initial");

        programGrid
                .addColumn(Program::getMinCGPAForGraduation)
                .setHeader("Minimum CGPA For Graduation");

        programGrid
                .addColumn(Program::getMinCreditForGraduation)
                .setHeader("Minimum Credits For Graduation");

        programGrid.setItems(userService.getPrograms());

        addProgramButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            H4 title = new H4("Add New Program");
            title.getStyle().set("text-align", "center");

            Button closeButton = new Button("Cancel", MINUS_SQUARE_O.create());
            closeButton.setWidth("200px");
            closeButton.getStyle()
                    .set("color", "white")
                    .set("background", "red");

            Button submitButton = new Button("Confirm Program", CHECK_SQUARE.create());
            submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);




            TextField idField = new TextField("Program ID", "integer id");
            TextField nameField = new TextField("Program Title", "Program Name");
            TextField coordinatorIdField = new TextField("Coordinator ID", "Initial of Coordinator");
            TextField minCGField = new TextField("Minimum CGPA For Graduation", "no more than 4");
            TextField minCreditField = new TextField("Minimum Credits For Graduation", "i.e. 120");

            programBinder
                    .forField(idField)
                    .asRequired()
                    .withConverter(new StringToIntegerConverter("Must be an integer"))
                    .bind(Program::getId, Program::setId);

            programBinder
                    .forField(nameField)
                    .asRequired()
                    .bind(Program::getTitle, Program::setTitle);

            programBinder
                    .forField(coordinatorIdField)
                    .asRequired()
                    .bind(Program::getCoordinatorId, Program::setCoordinatorId);

            programBinder
                    .forField(minCGField)
                    .asRequired()
                    .withConverter(new StringToDoubleConverter("must be a decimal number"))
                    .bind(Program::getMinCGPAForGraduation, Program::setMinCGPAForGraduation);

            programBinder
                    .forField(minCreditField)
                    .asRequired()
                    .withConverter(new StringToIntegerConverter("must be an integer"))
                    .bind(Program::getMinCreditForGraduation, Program::setMinCreditForGraduation);

            FormLayout formLayout = new FormLayout();

            formLayout.add(idField, nameField, coordinatorIdField, minCGField, minCreditField);

            submitButton.addClickListener(e -> {
                Program program = new Program();

                try{
                    programBinder.writeBean(program);
                    program.setCourseList(new ArrayList<>());
                    userService.saveProgram(program);
                    Notification.show("Saved " + program.getTitle());
                    programGrid.setItems(userService.getPrograms());

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

        add(addProgramButton, programGrid);
    }
}

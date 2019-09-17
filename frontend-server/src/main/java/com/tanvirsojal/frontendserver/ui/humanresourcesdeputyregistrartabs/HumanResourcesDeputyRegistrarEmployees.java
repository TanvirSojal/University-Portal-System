package com.tanvirsojal.frontendserver.ui.humanresourcesdeputyregistrartabs;

import com.tanvirsojal.frontendserver.model.Credential;
import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.Program;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class HumanResourcesDeputyRegistrarEmployees extends VerticalLayout {
    private Grid<Employee> employeeGrid;
    private Binder<Employee> employeeBinder;

    public HumanResourcesDeputyRegistrarEmployees(UserService userService, AuthenticationService authenticationService, HttpSession httpSession) {
        employeeGrid = new Grid<>(Employee.class);
        employeeBinder = new Binder<>();

        Button addEmployeeButton = new Button("Add Employee", PLUS.create());

        addEmployeeButton.getStyle()
                .set("margin-left", "300px");

        employeeGrid.setWidth("1200px");
        employeeGrid.getStyle()
                .set("margin-left", "300px");

        employeeGrid.removeAllColumns();
        employeeGrid
                .addColumn(Employee::getId)
                .setWidth("160px")
                .setFlexGrow(0)
                .setHeader("Initial");

        employeeGrid
                .addColumn(Employee::getName)
                .setHeader("Name");

        employeeGrid
                .addColumn(Employee::getDepartment)
                .setHeader("Department");

        employeeGrid
                .addColumn(Employee::getDesignation)
                .setHeader("Designation");

        employeeGrid
                .addColumn(Employee::getEmail)
                .setHeader("Email");

        employeeGrid.setItems(userService.getEmployees());


        addEmployeeButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            H4 title = new H4("Register New Employee");
            title.getStyle().set("text-align", "center");


            Button closeButton = new Button("Cancel", MINUS_SQUARE_O.create());
            closeButton.setWidth("200px");
            closeButton.getStyle()
                    .set("color", "white")
                    .set("background", "red");

            closeButton.addClickListener(e -> dialog.close());

            Button addButton = new Button("Add Employee", CHECK_SQUARE.create());
            addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


            VerticalLayout verticalLayout = new VerticalLayout();


            TextField idField = new TextField("Initial", "keep it memorable");
            TextField nameField = new TextField("Name", "Employee Name");
            TextField departmentField = new TextField("Department", "i.e. Admission, CSE");
            TextField designationField = new TextField("Designation", "i.e. Officer");
            EmailField emailField = new EmailField("Email", "Employee Email");
            TextField roleField = new TextField("Role", "Role of employee in WC Portal");
            roleField.setRequired(true);
            PasswordField passwordField = new PasswordField("Password", "use a strong password");
            passwordField.setRequired(true);
            PasswordField pconfirmPasswordField = new PasswordField("Confirm Password", "passwords must match");
            passwordField.setRequired(true);

            // setting up binder
            employeeBinder
                    .forField(idField)
                    .asRequired()
                    .bind(Employee::getId, Employee::setId);

            employeeBinder
                    .forField(nameField)
                    .asRequired()
                    .bind(Employee::getName, Employee::setName);

            employeeBinder
                    .forField(departmentField)
                    .asRequired()
                    .bind(Employee::getDepartment, Employee::setDepartment);

            employeeBinder
                    .forField(designationField)
                    .asRequired()
                    .bind(Employee::getDesignation, Employee::setDesignation);

            employeeBinder
                    .forField(emailField)
                    .asRequired()
                    .bind(Employee::getEmail, Employee::setEmail);

            FormLayout formLayout = new FormLayout();
            formLayout.add(idField, nameField, departmentField, designationField, emailField, roleField, passwordField, pconfirmPasswordField);

            addButton.addClickListener(e -> {
                if (!passwordField.getValue().equals(pconfirmPasswordField.getValue())) {
                    Notification.show("Passwords did not match, try again!");
                } else {
                    Employee employee = new Employee();
                    try {
                        employeeBinder.writeBean(employee);
                        System.out.println("[" + employee + "]");
                        Credential credential = new Credential(employee.getId(), passwordField.getValue(), roleField.getValue());
                        authenticationService.register(credential);

                        userService.saveEmployee(employee);
                        Notification.show("Saved " + employee.getName());
                        employeeGrid.setItems(userService.getEmployees());

                        dialog.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(addButton, closeButton);

            verticalLayout.add(title, formLayout, horizontalLayout);

            dialog.setWidth("1000px");
            dialog.add(verticalLayout);
            dialog.setCloseOnOutsideClick(false);
            dialog.open();

        });

        add(addEmployeeButton, employeeGrid);

    }
}

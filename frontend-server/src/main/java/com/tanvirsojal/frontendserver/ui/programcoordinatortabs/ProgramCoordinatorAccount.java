package com.tanvirsojal.frontendserver.ui.programcoordinatortabs;

import com.tanvirsojal.frontendserver.model.Credential;
import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import javax.servlet.http.HttpSession;

public class ProgramCoordinatorAccount extends VerticalLayout {
    public ProgramCoordinatorAccount(UserService userService, AuthenticationService authenticationService, HttpSession httpSession) {

        LoginToken loginToken = (LoginToken) httpSession.getAttribute("user");
        Employee employee = userService.getEmployee(loginToken.getUsername());

        H4 title = new H4("Your Information");
        title.getStyle()
                .set("margin-left", "430px");

        TextField idField = new TextField("ID");
        idField.setValue(employee.getId());
        idField.setEnabled(false);

        TextField nameField = new TextField("Name");
        nameField.setValue(employee.getName());
        nameField.setEnabled(false);

        TextField designationField = new TextField("Designation");
        designationField.setValue(employee.getDesignation());
        designationField.setEnabled(false);

        TextField departmentField = new TextField("Department");
        departmentField.setValue(employee.getDepartment());
        departmentField.setEnabled(false);

        TextField emailField = new TextField("Email");
        emailField.setValue(employee.getEmail());

        Button updateInformationButton = new Button("Update Information", VaadinIcon.INFO_CIRCLE_O.create());
        updateInformationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        updateInformationButton.addClickListener(event -> {
            String email = emailField.getValue();
            employee.setEmail(email);
            userService.updateEmployee(employee);
            Notification.show("Information updated!");
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(idField, nameField, designationField, departmentField, emailField, updateInformationButton);

        formLayout.setWidth("1000px");
        formLayout.getStyle()
                .set("margin-left", "430px");

        H4 passwordLabel = new H4("Update Password");
        passwordLabel.getStyle()
                .set("margin-top", "80px")
                .set("margin-left", "430px");

        PasswordField currentPasswordField = new PasswordField("Current Password");
        PasswordField newPasswordField = new PasswordField("New Password");
        PasswordField confirmNewPasswordField = new PasswordField("Confirm New Password");

        Button updatePasswordButton = new Button("Update Password", VaadinIcon.PASSWORD.create());
        updatePasswordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        updatePasswordButton.addClickListener(event -> {
            String currentPassword = currentPasswordField.getValue();

            LoginToken token = authenticationService.authenticate(employee.getId(), currentPassword);
            if (!token.getRole().equals(loginToken.getRole())){
                Notification.show("You provided wrong current password!");
            }
            else{
                String newPassword = newPasswordField.getValue();
                String confirmNewPassword = confirmNewPasswordField.getValue();

                if (newPassword.equals("")){
                    Notification.show("Password can not be empty!");
                }

                else{
                    if (!newPassword.equals(confirmNewPassword)){
                        Notification.show("Passwords did not match!");
                    }
                    else{
                        Credential newCredential = new Credential(loginToken.getUsername(), newPassword, loginToken.getRole());
                        authenticationService.updateCredentials(newCredential);
                        Notification.show("Password Updated!");
                    }
                }
            }

            currentPasswordField.clear();
            newPasswordField.clear();
            confirmNewPasswordField.clear();
        });

        FormLayout passwordLayout = new FormLayout();
        passwordLayout.add(currentPasswordField, newPasswordField, confirmNewPasswordField, updatePasswordButton);

        passwordLayout.setWidth("1000px");
        passwordLayout.getStyle()
                .set("margin-left", "430px");

        add(title, formLayout, passwordLabel, passwordLayout);
    }
}

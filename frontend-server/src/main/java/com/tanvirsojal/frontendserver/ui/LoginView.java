package com.tanvirsojal.frontendserver.ui;

import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("login")
public class LoginView extends Dialog {
    public LoginView(AuthenticationService authenticationService, HttpSession httpSession) {
        super();

        Image logo = new Image();
        logo.setSrc("https://i.imgur.com/TabTaEE.png");

        H4 title = new H4("Login Panel");
        title.getStyle().set("text-align", "center");

        TextField usernameField = new TextField("", "username");
        PasswordField passwordField = new PasswordField("", "password");

        Button loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickShortcut(Key.ENTER);

        Label statusLabel = new Label();

        loginButton.addClickListener(event -> {
            LoginToken loginToken = authenticationService.authenticate(usernameField.getValue(),
                    passwordField.getValue());

            System.out.println("FROM LOGINVIEW : " + loginToken.getRole());
            httpSession.setAttribute("user", loginToken);

            switch (loginToken.getRole()){
                case "none":
                    statusLabel.setText("Incorrect username/password");
                    break;

                case "student":
                case "admission-officer":
                case "program-coordinator":
                case "academic-deputy-registrar":
                case "human-resources-deputy-registrar":
                case "examination-officer":
                    loginButton.getUI().ifPresent(ui -> ui.navigate(loginToken.getRole()));
                    break;

                default:
                    break;
            }
        });

        usernameField.addFocusListener(event -> statusLabel.setText(""));
        passwordField.addFocusListener(event -> statusLabel.setText(""));

        Button forgotPasswordButton = new Button("Forgot Password");

        FormLayout formLayout = new FormLayout();
        formLayout.add(logo, title, usernameField, passwordField, loginButton, forgotPasswordButton, statusLabel);

        setWidth("260px");

        add(formLayout);

        setCloseOnOutsideClick(false);

        open();
    }
}

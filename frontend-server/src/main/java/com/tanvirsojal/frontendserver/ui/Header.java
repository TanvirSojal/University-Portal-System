package com.tanvirsojal.frontendserver.ui;

import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class Header extends HorizontalLayout {
    private LoginToken loginToken;
    private H5 nameLabel;

    public Header(HttpSession httpSession){
        super();


        loginToken = (LoginToken) httpSession.getAttribute("user");

        if (loginToken == null)
            loginToken = new LoginToken();

        System.out.println("Header called");
        System.out.println("Login Token: " + loginToken.getRole());

        H3 wcu = new H3("WC University Portal");
        wcu.getStyle()
                .set("margin-left", "60px");

        nameLabel = new H5();
        nameLabel.getStyle()
                .set("margin-right", "20px")
                .set("margin-top", "32px");

        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        logoutButton.getStyle()
                .set("margin-top", "24px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        logoutButton.addClickListener(event -> {
            logoutButton.getUI().ifPresent(ui -> ui.navigate("login"));
        });

        horizontalLayout.add(nameLabel, logoutButton);
        horizontalLayout.getStyle()
                .set("position", "absolute")
                .set("right", "0")
                .set("margin-right", "60px");

        add(wcu, horizontalLayout);
    }

    public LoginToken getLoginToken() {
        return loginToken;
    }

    public void setNameLabel(String name){
        nameLabel.setText("Welcome, " + name);
    }
}

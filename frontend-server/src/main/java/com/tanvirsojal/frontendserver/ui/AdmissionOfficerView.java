package com.tanvirsojal.frontendserver.ui;

import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.tanvirsojal.frontendserver.service.UserService;
import com.tanvirsojal.frontendserver.ui.admissionofficertabs.AdmissionOfficerAccountTab;
import com.tanvirsojal.frontendserver.ui.admissionofficertabs.AdmissionOfficerDashboardTab;
import com.tanvirsojal.frontendserver.ui.admissionofficertabs.AdmissionOfficerStudentAdmissionTab;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;
import java.util.*;

@Route("admission-officer")
public class AdmissionOfficerView extends VerticalLayout {
    private HttpSession httpSession;

    public AdmissionOfficerView(UserService userService, AuthenticationService authenticationService, HttpSession httpSession) {
        super();

        AppLayout appLayout = new AppLayout();
        Header header = new Header(httpSession);

        // working re-routing code for unauthorized access
//        System.out.println(header.getLoginToken().getRole());
//        if (!header.getLoginToken().getRole().equals("admission-officer")){
//            System.out.println(header.getLoginToken().getRole());
//            UI.getCurrent().navigate("login");
//            return;
//        }

        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if (!loginToken.getRole().equals("admission-officer")){
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });

        Employee employee = userService.getEmployee(header.getLoginToken().getUsername());
        header.setNameLabel(employee.getName());

        Tab dashboard = new Tab("Dashboard");
        Tab admission = new Tab("Admission");
        Tab account = new Tab("Account");
        Tabs tabs = new Tabs(dashboard, admission, account);

        tabs.getStyle()
                .set("margin", "auto")
                .set("margin-top", "10px");
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(header, tabs); // adding header and tabs on the UI

        // admission tab
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button button = new Button("Click me");

        Map< Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dashboard, new AdmissionOfficerDashboardTab());
        tabsToPages.put(admission, new AdmissionOfficerStudentAdmissionTab(userService, authenticationService));
        tabsToPages.put(account, new AdmissionOfficerAccountTab(userService, authenticationService, httpSession));

        Div dashboardPage = new Div();
        Div admissionPage = new Div(new AdmissionOfficerStudentAdmissionTab(userService, authenticationService)); admissionPage.setVisible(false);
        Div accountPage = new Div(new AdmissionOfficerAccountTab(userService, authenticationService, httpSession)); accountPage.setVisible(false);

        List<Div> pages = new ArrayList<>();

        pages.add(dashboardPage);
        pages.add(admissionPage);
        pages.add(accountPage);

//        tabs.addSelectedChangeListener(event -> {
//            pagesShown.forEach(page -> page.setVisible(false));
//            pagesShown.clear();
//
//            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
//            selectedPage.setVisible(true);
//            pagesShown.add(selectedPage);
//        });

        System.out.println("List size: " + pages.size());
        tabs.addSelectedChangeListener(event -> {
            pages.forEach(page -> page.setVisible(false));
            Div div = pages.get(tabs.getSelectedIndex());
            div.setVisible(true);
        });

        appLayout.addToNavbar(verticalLayout);
        add(appLayout, dashboardPage, admissionPage, accountPage);
    }

}

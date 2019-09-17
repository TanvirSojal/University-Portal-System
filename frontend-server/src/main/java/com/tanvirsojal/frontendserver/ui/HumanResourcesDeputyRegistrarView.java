package com.tanvirsojal.frontendserver.ui;

import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.tanvirsojal.frontendserver.service.UserService;
import com.tanvirsojal.frontendserver.ui.humanresourcesdeputyregistrartabs.HumanResourcesDeputyRegistrarAccount;
import com.tanvirsojal.frontendserver.ui.humanresourcesdeputyregistrartabs.HumanResourcesDeputyRegistrarDashboard;
import com.tanvirsojal.frontendserver.ui.humanresourcesdeputyregistrartabs.HumanResourcesDeputyRegistrarEmployees;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("human-resources-deputy-registrar")
public class HumanResourcesDeputyRegistrarView extends VerticalLayout {
    private HttpSession httpSession;

    public HumanResourcesDeputyRegistrarView(UserService userService, AuthenticationService authenticationService, HttpSession httpSession) {
        super();

        this.httpSession = httpSession;
        AppLayout appLayout = new AppLayout();
        Header header = new Header(httpSession);
//
//        // working re-routing code for unauthorized access
////        System.out.println(header.getLoginToken().getRole());
////        if (!header.getLoginToken().getRole().equals("admission-officer")){
////            System.out.println(header.getLoginToken().getRole());
////            UI.getCurrent().navigate("login");
////            return;
////        }
//
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if (!loginToken.getRole().equals("human-resources-deputy-registrar")){
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });
//
        Employee employee = userService.getEmployee(header.getLoginToken().getUsername());
        header.setNameLabel(employee.getName());
//
        Tab dashboard = new Tab("Dashboard");
//        Tab credentials = new Tab("Manage Credentials");
        Tab employees = new Tab("Register Employees");
        Tab account = new Tab("Account");
        Tabs tabs = new Tabs(dashboard, employees, account);

        tabs.getStyle()
                .set("margin", "auto")
                .set("margin-top", "10px");
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(header, tabs); // adding header and tabs on the UI


//        HorizontalLayout horizontalLayout = new HorizontalLayout();
//        Button button = new Button("Click me");
//
        Map< Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dashboard, new HumanResourcesDeputyRegistrarDashboard());
        tabsToPages.put(employees, new HumanResourcesDeputyRegistrarEmployees(userService, authenticationService, httpSession));
//        tabsToPages.put(credentials, new HumanResourcesDeputyRegistrarCredentials(userService, httpSession));
        tabsToPages.put(account, new HumanResourcesDeputyRegistrarAccount(userService, authenticationService, httpSession));


        Div dashboardPage = new Div(new HumanResourcesDeputyRegistrarDashboard());
        Div employeesPage = new Div(new HumanResourcesDeputyRegistrarEmployees(userService, authenticationService, httpSession)); employeesPage.setVisible(false);
//        Div credentialsPage = new Div(new HumanResourcesDeputyRegistrarCredentials(userService, httpSession)); credentialsPage.setVisible(false);
        Div accountPage = new Div(new HumanResourcesDeputyRegistrarAccount(userService, authenticationService, httpSession)); accountPage.setVisible(false);

        List<Div> pages = new ArrayList<>();

        pages.add(dashboardPage);
        pages.add(employeesPage);
//        pages.add(credentialsPage);
        pages.add(accountPage);

//        tabs.addSelectedChangeListener(event -> {
//            pagesShown.forEach(page -> page.setVisible(false));
//            pagesShown.clear();
//
//            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
//            selectedPage.setVisible(true);
//            pagesShown.add(selectedPage);
//        });
//
        System.out.println("List size: " + pages.size());
        tabs.addSelectedChangeListener(event -> {
            pages.forEach(page -> page.setVisible(false));
            Div div = pages.get(tabs.getSelectedIndex());
            div.setVisible(true);
        });
//
        appLayout.addToNavbar(verticalLayout);
        add(appLayout, dashboardPage, employeesPage, accountPage);
    }
}

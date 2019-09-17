package com.tanvirsojal.frontendserver.ui;

import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.tanvirsojal.frontendserver.service.UserService;
import com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs.AcademicDeputyRegistrarAccount;
import com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs.AcademicDeputyRegistrarDashboard;
import com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs.AcademicDeputyRegistrarAddCoursesTab;
import com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs.AcademicDeputyRegistrarAddProgramsTab;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("academic-deputy-registrar")
public class AcademicDeputyRegistrarView extends VerticalLayout {
    private HttpSession httpSession;

    public AcademicDeputyRegistrarView(UserService userService, AuthenticationService authenticationService, HttpSession httpSession) {
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
            if (!loginToken.getRole().equals("academic-deputy-registrar")){
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });

        Employee employee = userService.getEmployee(header.getLoginToken().getUsername());
        header.setNameLabel(employee.getName());

        Tab dashboard = new Tab("Dashboard");
        Tab programs = new Tab("Programs");
        Tab courses = new Tab("Courses");
        Tab account = new Tab("Account");
        Tabs tabs = new Tabs(dashboard, programs, courses, account);

        tabs.getStyle()
                .set("margin", "auto")
                .set("margin-top", "10px");
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(header, tabs); // adding header and tabs on the UI

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button button = new Button("Click me");

        Map< Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dashboard, new AcademicDeputyRegistrarDashboard());
        tabsToPages.put(programs, new AcademicDeputyRegistrarAddProgramsTab(userService));
        tabsToPages.put(courses, new AcademicDeputyRegistrarAddCoursesTab(userService));
        tabsToPages.put(account, new AcademicDeputyRegistrarAccount(userService, authenticationService, httpSession));

        Div dashboardPage = new Div();
        Div programsPage = new Div(new AcademicDeputyRegistrarAddProgramsTab(userService)); programsPage.setVisible(false);
        Div coursesPage = new Div(new AcademicDeputyRegistrarAddCoursesTab(userService)); coursesPage.setVisible(false);
        Div accountPage = new Div(new AcademicDeputyRegistrarAccount(userService, authenticationService, httpSession)); accountPage.setVisible(false);

        List<Div> pages = new ArrayList<>();

        pages.add(dashboardPage);
        pages.add(programsPage);
        pages.add(coursesPage);
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
        add(appLayout, dashboardPage, programsPage, coursesPage, accountPage);
    }
}

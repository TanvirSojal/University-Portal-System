package com.tanvirsojal.frontendserver.ui;

import com.tanvirsojal.frontendserver.model.Employee;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.AuthenticationService;
import com.tanvirsojal.frontendserver.service.UserService;
import com.tanvirsojal.frontendserver.ui.programcoordinatortabs.ProgramCoordinatorAccount;
import com.tanvirsojal.frontendserver.ui.programcoordinatortabs.ProgramCoordinatorDashboard;
import com.tanvirsojal.frontendserver.ui.programcoordinatortabs.ProgramCoordinatorRegistration;
import com.tanvirsojal.frontendserver.ui.studenttabs.StudentAccountTab;
import com.tanvirsojal.frontendserver.ui.studenttabs.StudentConvocationTab;
import com.tanvirsojal.frontendserver.ui.studenttabs.StudentDashboardTab;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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

@Route("student")
public class StudentView extends VerticalLayout {
    private HttpSession httpSession;

    public StudentView(UserService userService, AuthenticationService authenticationService, HttpSession httpSession) {
        super();

        this.httpSession = httpSession;

        AppLayout appLayout = new AppLayout();
        Header header = new Header(httpSession);


//        System.out.println("USER: " + httpSession.getAttribute("user"));
//        if (httpSession.getAttribute("user") == null){
//            UI.getCurrent().navigate("login");
//            return;
//        }

//        // working re-routing code for unauthorized access
        System.out.println(header.getLoginToken().getRole());
////        if (!header.getLoginToken().getRole().equals("admission-officer")){
////            System.out.println(header.getLoginToken().getRole());
////            UI.getCurrent().navigate("login");
////            return;
////        }
//

        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if (!loginToken.getRole().equals("student")){
                UI.getCurrent().navigate("login");
            }
        });
//
//        Employee employee = userService.getEmployee(header.getLoginToken().getUsername());
//        header.setNameLabel(employee.getName());
        Student student = userService.getStudent(header.getLoginToken().getUsername());
        header.setNameLabel(student.getName());
//
        Tab dashboard = new Tab("Dashboard");
        Tab convocation = new Tab("Convocation");
        Tab account = new Tab("Account");
        Tabs tabs = new Tabs(dashboard, convocation, account);

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
        tabsToPages.put(dashboard, new StudentDashboardTab(userService, httpSession));
        tabsToPages.put(convocation, new StudentConvocationTab(userService, httpSession));
        tabsToPages.put(account, new StudentAccountTab(userService, authenticationService, httpSession));

        Div dashboardPage = new Div(new StudentDashboardTab(userService, httpSession));
        Div convocationPage = new Div(new StudentConvocationTab(userService, httpSession)); convocationPage.setVisible(false);
        Div accountPage = new Div(new StudentAccountTab(userService, authenticationService, httpSession)); accountPage.setVisible(false);

        List<Div> pages = new ArrayList<>();

        pages.add(dashboardPage);
        pages.add(convocationPage);
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
        add(appLayout, dashboardPage, convocationPage, accountPage);
    }
}

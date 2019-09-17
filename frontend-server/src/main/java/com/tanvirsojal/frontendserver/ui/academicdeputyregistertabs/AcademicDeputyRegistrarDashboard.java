package com.tanvirsojal.frontendserver.ui.academicdeputyregistertabs;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AcademicDeputyRegistrarDashboard extends VerticalLayout {
    public AcademicDeputyRegistrarDashboard() {
        H4 welcomeText = new H4("Welcome to WC University portal.");
        welcomeText.getStyle()
                .set("margin-left", "810px")
                .set("margin-top", "300px");

        add(welcomeText);
    }
}

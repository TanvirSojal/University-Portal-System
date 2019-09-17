package com.tanvirsojal.frontendserver.ui.examinationofficertabs;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ExaminationOfficerDashboardTab extends VerticalLayout {
    public ExaminationOfficerDashboardTab() {
        H4 welcomeText = new H4("Welcome to WC University portal.");
        welcomeText.getStyle()
                .set("margin-left", "810px")
                .set("margin-top", "300px");

        add(welcomeText);
    }
}

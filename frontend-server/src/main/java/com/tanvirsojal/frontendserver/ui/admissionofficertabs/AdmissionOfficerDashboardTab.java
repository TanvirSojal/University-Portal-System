package com.tanvirsojal.frontendserver.ui.admissionofficertabs;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AdmissionOfficerDashboardTab extends VerticalLayout {
    public AdmissionOfficerDashboardTab() {
        H4 welcomeText = new H4("Welcome to WC University portal.");
        welcomeText.getStyle()
                .set("margin-left", "810px")
                .set("margin-top", "300px");

        add(welcomeText);
    }
}

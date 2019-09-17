package com.tanvirsojal.frontendserver.ui.examinationofficertabs;

import com.tanvirsojal.frontendserver.model.Convocation;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ExaminationOfficerConvocationTab extends VerticalLayout {
    Grid<Convocation> convocationGrid;
    public ExaminationOfficerConvocationTab(UserService userService) {
        H4 convocationLabel = new H4("Students registered for convocation");
        convocationGrid = new Grid<>(Convocation.class);

        convocationLabel.getStyle()
                .set("margin-left", "300px");
        convocationGrid.setWidth("1200px");
        convocationGrid.getStyle()
                .set("margin-left", "300px");

        convocationGrid.removeAllColumns();

        convocationGrid
                .addColumn(Convocation::getId)
                .setWidth("80px")
                .setHeader("Convocation ID");

        convocationGrid
                .addColumn(Convocation::getStudentId)
                .setHeader("Student ID");

        convocationGrid
                .addColumn(Convocation::getStudentName)
                .setHeader("Name");

        convocationGrid
                .addColumn(Convocation::getStudentCreditsCompleted)
                .setHeader("Credits Completed");

        convocationGrid
                .addColumn(Convocation::getStudentCGPA)
                .setHeader("CGPA");

        convocationGrid
                .addColumn(Convocation::getPaymentStatus)
                .setHeader("Payment Status");

        convocationGrid
                .addColumn(Convocation::getConfirmationStatus)
                .setHeader("Confirmation Status");

        convocationGrid.setItems(userService.getConvocations());

        add(convocationLabel, convocationGrid);
    }
}

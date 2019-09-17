package com.tanvirsojal.frontendserver.ui.studenttabs;

import com.tanvirsojal.frontendserver.model.Convocation;
import com.tanvirsojal.frontendserver.model.LoginToken;
import com.tanvirsojal.frontendserver.model.Student;
import com.tanvirsojal.frontendserver.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;

import javax.servlet.http.HttpSession;


public class StudentConvocationTab extends VerticalLayout {
    private Grid<Convocation> convocationGrid;
    private Student student;
    private Convocation convocation;

    public StudentConvocationTab(UserService userService, HttpSession httpSession) {
        convocationGrid = new Grid<>(Convocation.class);
        if (httpSession.getAttribute("user") != null){
            LoginToken loginToken = (LoginToken) httpSession.getAttribute("user");
            student = userService.getStudent(loginToken.getUsername());
        }

        H4 convocationLabel = new H4("Your Academic/Convocation Status");

        convocationLabel.getStyle()
                .set("margin-left", "300px");
        convocationGrid.setWidth("1200px");
        convocationGrid.setHeight("100px");
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

        System.out.println("Student logged in : " + student);
        if (student != null){
            userService.getConvocations().forEach(convocation -> {
                if (convocation.getStudentId().equals(student.getId())){
                    convocationGrid.setItems(convocation);
                    this.convocation = convocation;
                }
            });
        }

        H4 convocationEligibilityStatusLabel = new H4();
        convocationEligibilityStatusLabel.getStyle()
                .set("margin-left", "300px");


        Button applyButton = new Button("Apply For Convocation", VaadinIcon.DIPLOMA_SCROLL.create());
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button payButton = new Button("Make Payment", VaadinIcon.MONEY_DEPOSIT.create());
        payButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        if (student != null && student.getCreditsCompleted() >= student.getProgram().getMinCreditForGraduation()){

            if (convocation == null){
                convocationEligibilityStatusLabel.setText("You are eligible for Convocation");
                payButton.setEnabled(false);

                applyButton.addClickListener(event -> {
                    Dialog dialog = new Dialog();
                    H4 title = new H4("Apply for Convocation");
                    Text notice = new Text("You can only apply once for convocation. Please make sure you have your contact info properly updated. You have to pay 9000 BDT after you apply.");

                    Button submitButton = new Button("Submit Application", VaadinIcon.DIPLOMA_SCROLL.create());
                    submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                    submitButton.getStyle()
                            .set("margin-top", "20px");


                    submitButton.addClickListener(e -> {
                        Convocation newConvocation = new Convocation();
                        newConvocation.setStudent(student);
                        newConvocation.setId(userService.getConvocations().size() + 1); // setting id
                        newConvocation.setPaymentStatus("pending");
                        newConvocation.setConfirmationStatus("pending");

                        userService.saveConvocation(newConvocation);
                        convocationGrid.setItems(newConvocation);
                        Notification.show("Application submitted for " + student.getId() + " (" + student.getName() + ")");

                        applyButton.setEnabled(false);
                        payButton.setEnabled(true);

                        dialog.close();

                        UI.getCurrent().getPage().reload(); // forces page to reload, avoiding serial execution perks
                    });

                    dialog.setWidth("400px");

                    dialog.add(title, notice, submitButton);
                    dialog.open();
                });
            }
            else{
                convocationEligibilityStatusLabel.setText("You already applied for Convocation");
                applyButton.setEnabled(false);
                if (convocation.getPaymentStatus().equals("paid")){
                    payButton.setEnabled(false);
                }

                payButton.addClickListener(event -> {
                    Dialog dialog = new Dialog();
                    H4 title = new H4("Enter Payment Information");

                    ComboBox<String> paymentGatewayComboBox = new ComboBox<>("Payment Gateway", "bKash", "NexusPay", "Rocket", "iPay", "Nagad");
                    paymentGatewayComboBox.setValue("bKash");

                    TextField transactionIdField = new TextField("TrxID");
                    transactionIdField.setRequired(true);

                    Button submitButton = new Button("Submit Information", VaadinIcon.MONEY.create());
                    submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    submitButton.getStyle()
                            .set("margin-top", "20px");

                    submitButton.addClickListener(e -> {
                      if (convocation != null){
                          convocation.setPaymentStatus("paid"); // assuming the info are correct!!!
                          convocation = userService.updateConvocation(convocation);
                          convocationGrid.setItems(convocation);

                          payButton.setEnabled(false);
                          dialog.close();
                      }
                    });

                    FormLayout formLayout = new FormLayout();
                    formLayout.add(paymentGatewayComboBox, transactionIdField);

                    VerticalLayout verticalLayout = new VerticalLayout();
                    verticalLayout.add(title, formLayout, submitButton);

                    dialog.setWidth("400px");

                    dialog.add(verticalLayout);
                    dialog.open();
                });
            }
        } else{
            convocationEligibilityStatusLabel.setText("You are not eligible for Convocation");
            applyButton.setEnabled(false);
            payButton.setEnabled(false);
        }

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(applyButton, payButton);

        horizontalLayout.getStyle()
                .set("margin-left", "300px");

        add(convocationLabel, convocationGrid, convocationEligibilityStatusLabel, horizontalLayout);
    }
}

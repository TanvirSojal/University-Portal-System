package com.tanvirsojal.frontendserver.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
        super();
        //UI.getCurrent().navigate("login");
    }
}

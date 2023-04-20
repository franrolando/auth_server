package com.component.authserver.view;


import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@AnonymousAllowed
@Route("error-view")
public class ErrorView extends VerticalLayout {

    public ErrorView(){
        Paragraph errorMessage = new Paragraph("You tried to access");
        add(errorMessage);
    }

}

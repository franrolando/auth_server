package com.component.authserver.views;


import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Route(value = ErrorView.ERROR_VIEW_ROUTE)
@AnonymousAllowed
@Slf4j
public class ErrorView extends VerticalLayout implements HasUrlParameter<RouteParameters>, AfterNavigationObserver {
    public static final String ERROR_VIEW_ROUTE = "error-view";

    private Label errorMessageLabel;

    public ErrorView(){
        errorMessageLabel = new Label();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter RouteParameters error_message) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();
        log.debug("ErrorView query String {}", parametersMap);
        if (parametersMap.get("error") != null) {
            setLabelText(parametersMap.get("error").get(0));
        }
        add(errorMessageLabel);
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
    }

    private void setLabelText(String message){
        this.errorMessageLabel.setText(message);
    }
}

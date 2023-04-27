package com.component.authserver.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.server.StreamResource;

@Tag("button-anchor")
@JsModule("./component/button-anchor.js")
public class ButtonAnchor extends LitTemplate {

    @Id("button")
    private Button button;
    @Id("img")
    private Image img;
    @Id("anchor")
    private Anchor anchor;

    public ButtonAnchor(String src, String text, String href){
        StreamResource imageResource = new StreamResource("btn_google_signin_light_normal_web.png",
                () -> getClass().getResourceAsStream(src));
        img.setSrc(imageResource);
        img.addClickListener(e -> UI.getCurrent().navigate("/oauth2/authorization/google"));
        getElement().setProperty("src", src);
        getElement().setProperty("name", text);
        getElement().setProperty("href", href);
    }

}

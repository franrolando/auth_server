package com.component.authserver.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.server.StreamResource;
import org.springframework.util.StringUtils;

@Tag("button-anchor")
@JsModule("./components/button-anchor.js")
public class ButtonAnchor extends LitTemplate {

    @Id("button")
    private Button button;
    @Id("img")
    private Image img;
    @Id("anchor")
    private Anchor anchor;

    public ButtonAnchor(String src, String filename, String text, String href, String backgroundColor, String textColor){
        StreamResource imageResource = new StreamResource(filename,
                () -> getClass().getResourceAsStream(src));
        Image icon = new Image(imageResource,"");
        button.setText(text);
        button.setIcon(icon);
        button.getStyle().set("background-color", backgroundColor);
        button.getStyle().set("color", textColor);
        getElement().setProperty("src", src);
        getElement().setProperty("name", text);
        getElement().setProperty("href", href);
    }

}

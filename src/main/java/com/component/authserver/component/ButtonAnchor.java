package com.component.authserver.component;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

@Tag("button-anchor")
@JsModule("./component/button-anchor.js")
public class ButtonAnchor extends Component {

    @Id("button")
    private Button button;
    @Id("anchor")
    private Anchor anchor;

    public ButtonAnchor(String icon, String text, String href){
        getElement().setProperty("icon", icon);
        getElement().setProperty("name", text);
        getElement().setProperty("href", href);

    }

}

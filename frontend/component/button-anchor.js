
import { html, LitElement } from 'lit';
import '@vaadin/button';
import {PolymerElement} from "@polymer/polymer";

class ButtonAnchor extends LitElement {

    static get properties() {
        return {
            icon: { type: String },
            name: { type: String },
            href: { type: String }
        }
    }
    render() {
        return html`
            <div>
                <vaadin-button router-ignore id="button">
                    <vaadin-icon icon="${this.icon}"></vaadin-icon>
                    <a router-ignore id="anchor" href="${this.href}" style="text-decoration: none; color: black">${this.name}</a>
                </vaadin-button>
            </div>
        `;
    }

}

customElements.define('button-anchor', ButtonAnchor);
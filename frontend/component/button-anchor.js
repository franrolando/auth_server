
import { html, LitElement } from 'lit';
import '@vaadin/button';
import {PolymerElement} from "@polymer/polymer";

class ButtonAnchor extends LitElement {

    static get properties() {
        return {
            scr: { type: String },
            name: { type: String },
            href: { type: String }
        }
    }
    render() {
        return html`
                <vaadin-button router-ignore id="button" style=";z-index: 2">
                    <img src="${this.src}" id="img" onclick="" style="z-index: 3">
                    <a router-ignore id="anchor" href="${this.href}" style="text-decoration: none; color: black;background-color:transparent !important;z-index: 4">${this.name}</a>
                </vaadin-button>
        `;
    }

}

customElements.define('button-anchor', ButtonAnchor);

import { css, html, LitElement } from 'lit';
import '@vaadin/button';

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
            <a router-ignore id="anchor" href="${this.href}" style="text-decoration: none; color: black;background-color:transparent !important;">
                <vaadin-button id="button" style="width: 300px; height: 50px">
                    <img src="${this.src}" id="img">
                    <span>
                        ${this.name}
                    </span>
                </vaadin-button>
            </a>
        `;
    }

    static get styles() {
        return css`
    `;
    }

}

customElements.define('button-anchor', ButtonAnchor);
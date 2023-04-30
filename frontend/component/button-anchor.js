
import { html, LitElement } from 'lit';
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
                        <vaadin-button id="button" style="margin: 0; padding: 0">
                            <img src="${this.src}" id="img">${this.name}
                        </vaadin-button>
                    </a>
                
        `;
    }

}

customElements.define('button-anchor', ButtonAnchor);
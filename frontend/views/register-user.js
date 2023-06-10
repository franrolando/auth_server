import { html, LitElement } from 'lit';
import '@vaadin/button';
import '@vaadin/text-field';
import '@vaadin/password-field';

class RegisterUser extends LitElement {
    render() {
        return html`
            <div id="loginForm" style="width: 30%;height: 30%; margin: auto">
                <div style="align-items: center; display: flex; flex-direction: column" >
                    <vaadin-text-field id="usernameTextField" label="Username" placeholder="Username"></vaadin-text-field>
                    <vaadin-password-field id="passwordTextField" label="Password" placeholder="Password" ></vaadin-password-field>
                    <vaadin-button id="signUpButton">Sign up</vaadin-button>
                </div>
            </div>
      
        `;
    }
}

customElements.define('register-user', RegisterUser);
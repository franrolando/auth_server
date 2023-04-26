import { html, LitElement } from 'lit';
import '@vaadin/button';
import '@vaadin/text-field';
import '@vaadin/password-field';

class LoginView extends LitElement {
    render() {
        return html`
            <div id="loginForm" style="width: 30%;height: 30%; margin: auto">
                <div style="align-items: center; display: flex; flex-direction: column" >
                    <vaadin-text-field id="usernameTextField" label="Username" placeholder="Username"></vaadin-text-field>
                    <vaadin-password-field id="passwordTextField" label="Password" placeholder="Password" ></vaadin-password-field>
                    <vaadin-button id="forgotPasswordButton">Forgot password</vaadin-button>
                    <vaadin-button id="loginButton">Sign in</vaadin-button>
                </div>
                <div id="oauthDiv" style="display: grid;grid-template-columns: repeat(3,1fr)">

                </div>
            </div>
      
        `;
    }
}

customElements.define('login-view', LoginView);
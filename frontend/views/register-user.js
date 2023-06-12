import { html, LitElement } from 'lit';
import '@vaadin/button';
import '@vaadin/text-field';
import '@vaadin/password-field';

class RegisterUser extends LitElement {
    render() {
        return html`
            <div id="loginForm" style="width: 30%;height: 30%; margin: auto">
                <div style="align-items: center; display: flex; flex-direction: column; flex: 50%" >
                    <vaadin-text-field id="usernameTextField" label="Username" placeholder="Username"></vaadin-text-field>
                    <vaadin-password-field id="passwordTextField" label="Password" placeholder="Password" ></vaadin-password-field>
                    <vaadin-text-field id="nameTextField" label="name" placeholder="Name" ></vaadin-text-field>
                    <vaadin-text-field id="surnameTextField" label="surname" placeholder="Surname" ></vaadin-text-field>
                    <vaadin-text-field id="countryTextField" label="country" placeholder="Country" ></vaadin-text-field>
                    <vaadin-text-field id="stateTextField" label="state" placeholder="State" ></vaadin-text-field>
                    <vaadin-text-field id="cityTextField" label="city" placeholder="City" ></vaadin-text-field>
                    <vaadin-button id="signUpButton">Sign up</vaadin-button>
                </div>
            </div>
      
        `;
    }
}

customElements.define('register-user', RegisterUser);
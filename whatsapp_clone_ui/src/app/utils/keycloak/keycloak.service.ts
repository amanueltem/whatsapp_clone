import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js'
@Injectable({
  providedIn: 'root',
})
export class KeycloakService {
  private _keycloack: Keycloak | undefined;
  get keyCloak(){
    if(!this._keycloack){
      this._keycloack= new Keycloak(
        {
          url: 'http://localhost:8080',
          realm: 'whatsapp-clone',
          clientId: 'whatsapp-clone-app'
        }
      );
    }
    return this._keycloack;
  }
  async init(){
    const authenticated=await this.keyCloak.init(
      {
        onLoad: 'login-required'
      }
    )
  }
  async login(){
    await this.keyCloak.login()
  }
  get userId(){
    return this.keyCloak?.tokenParsed?.sub as string;
  }
  get isTokenValid(){
    return !this.keyCloak.isTokenExpired;
  }
  get fullName(){
    return this.keyCloak.tokenParsed?.["name"]as string;
  }
  logout(){
    return this.keyCloak.logout(
      {
        redirectUri:"http://localhost:4200"
      }
    );
  }
  accountManagement(){
    return this.keyCloak.accountManagement();
  }
}

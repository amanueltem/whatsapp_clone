import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { Inject } from '@angular/core';
import { KeycloakService } from '../keycloak/keycloak.service';

export const keycloakHttpInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService: KeycloakService= Inject(KeycloakService)
  const token=keycloakService.keyCloak.token;
  if(token){
    const authReq=req.clone(
      {
        headers:new HttpHeaders(
          {
            Authorization: `Bearer ${token}`
          }
        ),

      });
    return next(authReq);
  }
  return next(req);
};

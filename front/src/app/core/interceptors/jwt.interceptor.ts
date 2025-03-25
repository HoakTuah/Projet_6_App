import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = localStorage.getItem('token');
    console.log('JwtInterceptor - Original Request Headers:', request.headers.keys());
    
    if (token) {
      console.log('JwtInterceptor - Token found:', token.substring(0, 20) + '...');
      
      // Créer des headers complets
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        'Accept': 'application/json'
      });

      // Cloner la requête avec les nouveaux headers
      request = request.clone({
        headers: headers
      });

      console.log('JwtInterceptor - Modified Request Headers:', request.headers.keys());
      console.log('JwtInterceptor - Auth Header:', request.headers.get('Authorization'));
    }

    return next.handle(request);
  }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { RegisterRequest, RegisterResponse, User } from '../interfaces/Register.Interface';
import { LoginRequest, LoginResponse } from '../interfaces/Login.Interface';
import { environment } from '../../../../environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private router: Router) { }

  register(registerData: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.apiUrl}/api/auth/register`, registerData);
  }

  login(loginData: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/api/auth/login`, loginData).pipe(
      tap(response => {
        if (response.success) {
          localStorage.setItem('token', response.token);
          // Créer l'objet user avec les données de la réponse
          const userData = {
            id: response.userId,
            username: response.username,
            email: response.email,
            subscribedTopics: response.subscribedTopics
          };
          localStorage.setItem('user', JSON.stringify(userData));
          this.router.navigate(['/MDD/articles']);
        }
      })
    );
  }

  logout(): void {

    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.router.navigate(['/auth/login']);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token') && !!localStorage.getItem('user');
  }

  getCurrentUser(): User | null {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        return JSON.parse(userStr) as User;
      } catch (e) {
        console.error('Error parsing user data:', e);
        return null;
      }
    }
    return null;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

}
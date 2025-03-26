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

  //=============================================================
  // API base URL from environment configuration
  //=============================================================
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private router: Router) { }

  //=============================================================
  //  Register User
  //=============================================================

  register(registerData: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.apiUrl}/api/auth/register`, registerData);
  }

  //=============================================================
  //  Login User
  //=============================================================

  login(loginData: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/api/auth/login`, loginData).pipe(
      tap(response => {
        if (response.success) {
          // Store token and user data in local storage
          localStorage.setItem('token', response.token);

          // Create user object with response data
          const userData = {
            id: response.userId,
            username: response.username,
            email: response.email,
            subscribedTopics: response.subscribedTopics
          };

          // Store user data in local storage
          localStorage.setItem('user', JSON.stringify(userData));

          // Navigate to main page after successful login
          this.router.navigate(['/MDD/articles']);
        }
      })
    );
  }

  //=============================================================
  //  Logout User
  //=============================================================

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.router.navigate(['/auth/login']);
  }

  //=============================================================
  //  Check if User is Logged In
  //=============================================================

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token') && !!localStorage.getItem('user');
  }

  //=============================================================
  //  Get Current User
  //=============================================================

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

  //=============================================================
  //  Get Token
  //=============================================================

  getToken(): string | null {
    return localStorage.getItem('token');
  }

}
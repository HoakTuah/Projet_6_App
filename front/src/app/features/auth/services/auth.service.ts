import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { RegisterRequest, RegisterResponse } from '../interfaces/RegisterRequest.Interface';
import { LoginRequest, LoginResponse } from '../interfaces/LoginRequest.Interface';
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
          this.router.navigate(['/MDD/articles']);
        }
      })
    );
  }

  logout(): void {
    // Supprimer les informations d'authentification du localStorage
    localStorage.removeItem('user');
    localStorage.removeItem('token'); // si vous stockez un token
    
    // Vous pouvez ajouter d'autres nettoyages si nécessaire
    
    // Rediriger vers la page de connexion
    this.router.navigate(['/auth/login']);
  }

  // Méthode utile pour vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }


}
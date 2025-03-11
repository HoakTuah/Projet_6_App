import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // Vérifier si l'utilisateur est connecté
    const user = localStorage.getItem('user');
    
    if (user) {
      // Si l'utilisateur est connecté, autoriser l'accès
      return true;
    }

    // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
    this.router.navigate(['/auth/login']);
    return false;
  }
}
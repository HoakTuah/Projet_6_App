import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // Check if user is logged in
    const user = localStorage.getItem('user');
    
    if (user) {
       // If user is logged in, allow access
      return true;
    }

    // If user is not logged in, redirect to login page
    this.router.navigate(['/auth/login']);
    return false;
  }
}
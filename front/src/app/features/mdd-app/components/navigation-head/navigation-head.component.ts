import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-navigation-head',
  templateUrl: './navigation-head.component.html',
  styleUrls: ['./navigation-head.component.scss']
})
export class NavigationHeadComponent {
  isMenuOpen = false;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  //=============================================================
  //  Authentication Methods
  //=============================================================
  onLogout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  //=============================================================
  //  Menu Control Methods
  //=============================================================
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    if (this.isMenuOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'auto';
    }
  }

  closeMenu() {
    this.isMenuOpen = false;
  }

  //=============================================================
  //  Navigation Methods
  //=============================================================
  navigateToProfile() {
    this.router.navigate(['/MDD/profile']);
    // If the menu is open, close it
    if (this.isMenuOpen) {
      this.closeMenu();
    }
  }
}
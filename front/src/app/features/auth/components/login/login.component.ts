import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/LoginRequest.Interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;
  successMessage: string = '';
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
    // Récupérer les paramètres de l'URL
    this.route.queryParams.subscribe(params => {
      if (params['registered'] === 'true') {
        this.successMessage = params['message'] || 'Inscription réussie !';
      }
    });
  }
  

  onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';

      const loginData: LoginRequest = this.loginForm.value;

      this.authService.login(loginData).subscribe({
        next: (response) => {
          console.log('Login successful', response);
          this.isLoading = false;

          if (response.success) {
            // Stocker les informations de l'utilisateur si nécessaire
            localStorage.setItem('user', JSON.stringify({
              id: response.userId,
              username: response.username,
              email: response.email
            }));
            
            // Rediriger vers la page d'accueil ou le tableau de bord
            this.router.navigate(['/topics']);
          } else {
            this.errorMessage = response.message || 'Erreur de connexion';
          }
        },
        error: (error) => {
          console.error('Login error', error);
          this.isLoading = false;
          this.errorMessage = error.error?.message || 'Erreur de connexion';
        }
      });
    }
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
  
  goBack() {
    this.router.navigate(['/']);
  }
}
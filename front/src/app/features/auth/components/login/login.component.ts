import { Component, OnInit , OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/LoginRequest.Interface';
import { Subscription } from 'rxjs';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  isLoading: boolean = false;
  private messageSubscription: Subscription | undefined;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private messageService: MessageService
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
    // S'abonner au MessageService
    this.messageSubscription = this.messageService.currentMessage.subscribe(message => {
      console.log('Message reçu dans login:', message); // Pour déboguer
      if (message) {
        this.successMessage = message;
        // Optionnel : effacer le message après quelques secondes
        setTimeout(() => {
          this.successMessage = '';
          this.messageService.clearMessage();}, 3000);
      }
    });
  
    // Garder aussi la logique existante pour les paramètres d'URL
    this.route.queryParams.subscribe(params => {
      if (params['registered'] === 'true') {
        this.successMessage = params['message'] || 'Inscription réussie !';
      }
    });
  }

  ngOnDestroy() {
    // Se désabonner pour éviter les fuites de mémoire
    if (this.messageSubscription) {
        this.messageSubscription.unsubscribe();
    }
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
            
            // Rediriger vers la page d'accueil
            this.router.navigate(['/MDD/articles']);
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
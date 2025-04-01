import { Component, OnInit , OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/Login.Interface';
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

  //=============================================================
  //  Initialization
  //=============================================================
  ngOnInit() {
    // Subscribe to MessageService for notifications
    this.messageSubscription = this.messageService.currentMessage.subscribe(message => {
      if (message) {
        this.successMessage = message;
        setTimeout(() => {
          this.successMessage = '';
          this.messageService.clearMessage();}, 3000);
      }
    });
  
    // Check URL params for registration success
    this.route.queryParams.subscribe(params => {
      if (params['registered'] === 'true') {
        this.successMessage = params['message'] || 'Inscription réussie !';
      }
    });
  }

  ngOnDestroy() {
    // Unsubscribe to prevent memory leaks
    if (this.messageSubscription) {
        this.messageSubscription.unsubscribe();
    }
}

  //=============================================================
  //  Form Submission Handler
  //=============================================================

  onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';

      const loginData: LoginRequest = this.loginForm.value;

      // Call auth service to authenticate user
      this.authService.login(loginData).subscribe({
        next: (response) => {
          this.isLoading = false;

          if (response.success) {
            // Store user information if needed
            localStorage.setItem('user', JSON.stringify({
              id: response.userId,
              username: response.username,
              email: response.email
            }));
            
            // Redirect to home page
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

  //=============================================================
  //  Navigation Methods
  //=============================================================
  goToRegister() {
    this.router.navigate(['/register']);
  }
  
  goBack() {
    this.router.navigate(['/']);
  }
}
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/Register.Interface';
import { MessageService } from '../../services/message.service';  

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;
  
  constructor(
    private fb: FormBuilder, 
    private router: Router,
    private authService: AuthService,
    private messageService: MessageService
  ) 
  {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
  
  //=============================================================
  //  Form Submission Handler
  //=============================================================

  onSubmit() {
    if (this.registerForm.valid) {
        this.isLoading = true;
        this.errorMessage = '';
      
        const registerData: RegisterRequest = this.registerForm.value;
      
         // Call auth service to register user
        this.authService.register(registerData).subscribe({
          next: (response) => {
          
          this.isLoading = false;
          
          if (response.success) {
            // Redirect to login page with success message
            this.messageService.setMessage('Inscription réussie ! Vous pouvez maintenant vous connecter.');
            this.router.navigate(['/auth/login']);
          } else {
            this.errorMessage = response.message || 'Une erreur est survenue lors de l\'inscription';
            setTimeout(() => {
              this.errorMessage = '';
            }, 3000);
          }
        },

        error: (error) => {
          console.error('Registration error', error);
          this.isLoading = false;
          this.errorMessage = error.error?.message || 'Une erreur est survenue lors de l\'inscription';
          
          setTimeout(() => {
            this.errorMessage = '';
          }, 3000);
        }
      });
    }
  }
  
  //=============================================================
  //  Navigation Methods
  //=============================================================
  goBack() {
    this.router.navigate(['/']);
  }
}
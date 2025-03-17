import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  currentUser: any;
  errorMessage: string = '';
  successMessage: string = '';
  isLoading: boolean = false;
  subscriptions = [
    {
      id: 1,
      title: 'Titre du thème',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard...'
    },
    {
      id: 2,
      title: 'Titre du thème',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard...'
    }
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private profileService: ProfileService
  ) {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['']
    });
  }

  ngOnInit(): void {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      this.currentUser = JSON.parse(userStr);
      this.profileForm.patchValue({
        username: this.currentUser.username,
        email: this.currentUser.email
      });
    } else {
      this.router.navigate(['/auth/login']);
    }
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const updateData: any = {};
    if (this.profileForm.value.username !== this.currentUser.username) {
      updateData.username = this.profileForm.value.username;
    }
    if (this.profileForm.value.email !== this.currentUser.email) {
      updateData.email = this.profileForm.value.email;
    }
    if (this.profileForm.value.password) {
      updateData.password = this.profileForm.value.password;
    }

    if (Object.keys(updateData).length === 0) {
      this.successMessage = 'Aucune modification n\'a été apportée à votre profil';
      this.isLoading = false;
      return;
    }

    this.profileService.updateProfile(this.currentUser.id, updateData)
      .subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response.success) {
            // Update the stored user data
            const updatedUser = {
              ...this.currentUser,
              username: response.username,
              email: response.email
            };
            localStorage.setItem('user', JSON.stringify(updatedUser));
            
            this.successMessage = 'Profil mis à jour avec succès';
            this.currentUser = updatedUser;
            
            // Clear password field
            this.profileForm.patchValue({
              password: ''
            });
          } else {
            this.errorMessage = response.message || 'Une erreur est survenue lors de la mise à jour du profil';
          }
        },
        error: (error) => {
          this.isLoading = false;
          this.errorMessage = error.error?.message || 'Une erreur est survenue lors de la mise à jour du profil';
          console.error('Error updating profile:', error);
        }
      });
  }

  unsubscribe(id: number): void {
    console.log('Unsubscribed from theme:', id);
    this.subscriptions = this.subscriptions.filter(sub => sub.id !== id);
  }
}
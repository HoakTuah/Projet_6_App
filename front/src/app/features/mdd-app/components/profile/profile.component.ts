import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';
import { TopicService } from '../../services/topic.service';
import { ProfileService } from '../../services/profile.service'; 
import { Topic } from '../../interfaces/Topic.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs/operators';

interface UserResponse {
  id: number;
  username: string;
  email: string;
  token?: string;
  [key: string]: any;
}

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
  subscriptions: Topic[] = [];
  isLoadingSubscriptions: boolean = false;
  subscriptionError: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private topicService: TopicService,
    private profileService: ProfileService
  ) {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['']
    });
  }

  //=============================================================
  //  Lifecycle Hooks
  //============================================================= 
  ngOnInit(): void {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      this.currentUser = JSON.parse(userStr);
      this.profileForm.patchValue({
        username: this.currentUser.username,
        email: this.currentUser.email
      });
      
      this.loadSubscriptions();
    } else {
      this.router.navigate(['/auth/login']);
    }
  }

  //=============================================================
  //  Subscription Loading Methods
  //=============================================================
  loadSubscriptions(): void {
    if (this.isLoadingSubscriptions) return; // Prevent multiple simultaneous calls
    
    this.isLoadingSubscriptions = true;
    this.subscriptionError = '';

    this.topicService.getSubscribedTopics()
      .pipe(finalize(() => this.isLoadingSubscriptions = false))
      .subscribe({
        next: (topics: Topic[]) => {
          this.subscriptions = topics;
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error loading subscriptions:', error);
          if (error.status === 401) {
            this.router.navigate(['/auth/login']);
          } else {
            this.subscriptionError = 'Erreur lors du chargement des abonnements';
          }
        }
      });
  }

  //=============================================================
  //  Profile Update Methods
  //=============================================================
  private updateLocalStorage(userData: UserResponse, token?: string): void {

    this.currentUser = {
      ...this.currentUser,
      ...userData
    };
    localStorage.setItem('user', JSON.stringify(this.currentUser));

    if (token) {
      localStorage.setItem('token', token);
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
    const isEmailChange = this.profileForm.value.email !== this.currentUser.email;
    
    if (this.profileForm.value.username !== this.currentUser.username) {
      updateData.username = this.profileForm.value.username;
    }
    if (isEmailChange) {
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
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.profileForm.patchValue({ password: '' });
        })
      )
      .subscribe({
        next: (response: UserResponse) => {
          // Update local storage with new user data and token if provided
          this.updateLocalStorage(response, response.token);
          
          this.successMessage = 'Profil mis à jour avec succès';
          
        },
        error: (error: HttpErrorResponse) => {
          this.errorMessage = error.error?.message || 'Une erreur est survenue lors de la mise à jour du profil';
          console.error('Error updating profile:', error);
          
          if (error.status === 401) {
            this.router.navigate(['/auth/login']);
          }
        }
      });
  }

  //=============================================================
  //  Topic Unsubscription Methods
  //=============================================================
  unsubscribeFromTopic(topicId: number): void {
    this.topicService.unsubscribeFromTopic(topicId).subscribe({
      next: () => {
        this.subscriptions = this.subscriptions.filter(topic => topic.id !== topicId);
      },
      error: (error: HttpErrorResponse) => {
        this.subscriptionError = 'Erreur lors du désabonnement';
        console.error('Error unsubscribing:', error);
        
        if (error.status === 401) {
          this.router.navigate(['/auth/login']);
        }
      }
    });
  }
}
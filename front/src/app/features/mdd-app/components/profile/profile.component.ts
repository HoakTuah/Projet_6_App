import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';
import { TopicService } from '../../services/topic.service';
import { ProfileService } from '../../services/profile.service'; // Ajout de l'import
import { Topic } from '../../interfaces/Topic.interface';
import { HttpErrorResponse } from '@angular/common/http'; // Pour typer les erreurs

interface UserResponse {
  id: number;
  username: string;
  email: string;
  [key: string]: any; // Pour les autres propriétés possibles
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
    private profileService: ProfileService // Ajout du service
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
      
      this.loadSubscriptions();
    } else {
      this.router.navigate(['/auth/login']);
    }
  }

  loadSubscriptions(): void {
    this.isLoadingSubscriptions = true;
    this.subscriptionError = '';

    this.topicService.getSubscribedTopics().subscribe({
      next: (topics: Topic[]) => {
        this.subscriptions = topics;
        this.isLoadingSubscriptions = false;
      },
      error: (error: HttpErrorResponse) => {
        this.subscriptionError = 'Erreur lors du chargement des abonnements';
        this.isLoadingSubscriptions = false;
        console.error('Error loading subscriptions:', error);
        
        if (error.status === 401) {
          this.router.navigate(['/auth/login']);
        }
      }
    });
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

    this.profileService.updateProfile(this.currentUser.id, updateData).subscribe({
      next: (response: UserResponse) => {
        this.currentUser = {
          ...this.currentUser,
          ...response
        };
        localStorage.setItem('user', JSON.stringify(this.currentUser));
        
        this.successMessage = 'Profil mis à jour avec succès';
        this.isLoading = false;
        
        this.profileForm.patchValue({ password: '' });
      },
      error: (error: HttpErrorResponse) => {
        this.isLoading = false;
        this.errorMessage = error.error?.message || 'Une erreur est survenue lors de la mise à jour du profil';
        console.error('Error updating profile:', error);
        
        if (error.status === 401) {
          this.router.navigate(['/auth/login']);
        }
      }
    });
  }

  unsubscribeFromTopic(topicId: number): void {
    this.topicService.unsubscribeFromTopic(topicId).subscribe({
      next: () => {
        // Retirer le topic de la liste des abonnements
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
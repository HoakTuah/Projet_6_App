import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
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
    private authService: AuthService
  ) {
    this.profileForm = this.fb.group({
      username: ['Username', Validators.required],
      email: ['email@email.fr', [Validators.required, Validators.email]],
      password: ['', Validators.minLength(6)]
    });
  }

  ngOnInit(): void {
    // Here you would typically load the user data
    const user = this.authService.getCurrentUser();
    if (user) {
      this.profileForm.patchValue({
        username: user.username,
        email: user.email
      });
    }
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      console.log('Profile updated:', this.profileForm.value);
      // Here you would send the updated profile to your backend
    }
  }

  unsubscribe(id: number): void {
    console.log('Unsubscribed from theme:', id);
    // Here you would call your API to unsubscribe
    this.subscriptions = this.subscriptions.filter(sub => sub.id !== id);
  }
}
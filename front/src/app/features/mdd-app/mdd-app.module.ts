import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// Material Modules
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
// Composants
import { MddAppComponent } from './mdd-app.component';
import { NavigationHeadComponent } from './navigation-head/navigation-head.component';
import { PostComponent } from './post/post.component';
import { MddAppRoutingModule } from './mdd-app-routing.module';
import { TopicComponent } from './topic/topic.component';
import { PostDetailComponent } from './post-detail/post-detail.component';
import { PostCreateComponent } from './post-create/post-create.component';
import { ProfileComponent } from './profile/profile.component';


@NgModule({
  declarations: [
    MddAppComponent,
    NavigationHeadComponent,
    PostComponent,
    TopicComponent,
    PostDetailComponent,
    PostCreateComponent,
    ProfileComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MddAppRoutingModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule
  ]
})
export class MddAppModule { }
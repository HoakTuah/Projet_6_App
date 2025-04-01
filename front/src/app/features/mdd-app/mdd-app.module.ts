import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

// Material Modules
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// Composants
import { MddAppComponent } from './mdd-app.component';
import { NavigationHeadComponent } from './components/navigation-head/navigation-head.component';
import { PostComponent } from './components/post/post-list/post-list.component';
import { MddAppRoutingModule } from './mdd-app-routing.module';
import { TopicComponent } from './components/topic/topic.component';
import { PostDetailComponent } from './components/post/post-detail/post-detail.component';
import { PostCreateComponent } from './components/post/post-create/post-create.component';
import { ProfileComponent } from './components/profile/profile.component';
import { TopicService } from './services/topic.service';
import { PostService } from './services/post.service';
import { ProfileService } from './services/profile.service';


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
    ReactiveFormsModule,
    MddAppRoutingModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatProgressSpinnerModule
  ],
  providers: [TopicService,
    PostService,
    ProfileService
  ]
})
export class MddAppModule { }
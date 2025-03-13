import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// Composants
import { MddAppComponent } from './mdd-app.component';
import { NavigationHeadComponent } from './navigation-head/navigation-head.component';
import { PostComponent } from './post/post.component';
import { MddAppRoutingModule } from './mdd-app-routing.module';

// Material Modules
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    MddAppComponent,
    NavigationHeadComponent,
    PostComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MddAppRoutingModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatCardModule
  ]
})
export class MddAppModule { }
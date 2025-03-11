import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicsComponent } from './topics/topics.component';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MddAppRoutingModule } from './mdd-app-routing.module';

@NgModule({
  declarations: [
    TopicsComponent
  ],
  imports: [
    CommonModule,
    MddAppRoutingModule, // Importer le module de routing
    MatIconModule,
    MatMenuModule,
    MatButtonModule
  ]
})
export class MddAppModule { }
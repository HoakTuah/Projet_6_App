import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopicsComponent } from './topics/topics.component';

const routes: Routes = [
  {
    path: '',
    component: TopicsComponent
  }
  // Vous pouvez ajouter d'autres routes ici
  // { path: 'create', component: CreateTopicComponent },
  // { path: ':id', component: TopicDetailComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MddAppRoutingModule { }
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MddAppComponent } from './mdd-app.component';
import { PostComponent } from './post/post.component';
import { TopicComponent } from './topic/topic.component';

const routes: Routes = [
  {
    path: '',
    component: MddAppComponent,
    children: [
      { path: 'articles', component: PostComponent },
      { path: 'themes', component: TopicComponent },
      { path: '', redirectTo: 'articles', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MddAppRoutingModule { }
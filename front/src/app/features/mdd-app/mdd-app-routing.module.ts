import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MddAppComponent } from './mdd-app.component';
import { PostComponent } from './components/post/post-list/post-list.component';
import { TopicComponent } from './components/topic/topic.component';
import { PostDetailComponent } from './components/post/post-detail/post-detail.component';
import { PostCreateComponent } from './components/post/post-create/post-create.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  {
    path: '',
    component: MddAppComponent,
    children: [
      { path: 'articles', component: PostComponent },
      { path: 'articles/create', component: PostCreateComponent },
      { path: 'articles/:id', component: PostDetailComponent },
      { path: 'themes', component: TopicComponent },
      { path: 'profile', component: ProfileComponent },
      { path: '', redirectTo: 'articles', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MddAppRoutingModule { }
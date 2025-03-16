import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MddAppComponent } from './mdd-app.component';
import { PostComponent } from './post/post.component';
import { TopicComponent } from './topic/topic.component';
import { PostDetailComponent } from './post-detail/post-detail.component';
import { PostCreateComponent } from './post-create/post-create.component';
const routes: Routes = [
  {
    path: '',
    component: MddAppComponent,
    children: [
      { path: 'articles', component: PostComponent },
      { path: 'articles/create', component: PostCreateComponent },
      { path: 'articles/:id', component: PostDetailComponent },
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
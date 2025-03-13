import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MddAppComponent } from './mdd-app.component';
import { PostComponent} from './post/post.component';

const routes: Routes = [
  {
    path: '',
    component: MddAppComponent,
    children: [
      { path: '', component: PostComponent }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MddAppRoutingModule { }
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './component/board/board.component';
import { PostModifyComponent } from './component/board/post-modify.component';
import { PostViewComponent } from './component/board/post-view.component';
import { PostComponent } from './component/board/post.component';
import { HomeComponent } from './component/home.component';
import { LogoutComponent } from './component/logout/logout.component';
import { MyinfoComponent } from './component/member/myinfo/myinfo.component';
import { SigninComponent } from './component/member/signin.component';
import { SignupComponent } from './component/member/signup.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'signin', component: SigninComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'myinfo', component: MyinfoComponent, canActivate: [AuthGuard]},
  {path: 'board/:boardName', component: BoardComponent},
  {path: 'board/:boardName/post', component: PostComponent, canActivate: [AuthGuard]},
  {path: 'board/:boardName/post/:postId', component: PostViewComponent},
  {path: 'board/:boardName/post/:postId/modify', component: PostModifyComponent, canActivate: [AuthGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

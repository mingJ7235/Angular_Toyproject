import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home.component';
import { LogoutComponent } from './component/logout/logout.component';
import { SigninComponent } from './component/member/signin.component';
import { SignupComponent } from './component/member/signup.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'signin', component: SigninComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'logout', component: LogoutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

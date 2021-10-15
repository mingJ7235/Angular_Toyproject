import { HttpRequestInterceptorService } from './service/rest-api/common/http-request-interceptor.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './modules/material/material.module'
import { FlexLayoutModule } from '@angular/flex-layout';
import { HomeComponent } from './component/home.component';
import { SigninComponent } from './component/member/signin.component';
import { SignupComponent } from './component/member/signup.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SignService } from './service/rest-api/sign.service';
import { LogoutComponent } from './component/logout/logout.component';
import { MyinfoComponent } from './component/member/myinfo/myinfo.component';
import { MyinfoService } from './service/rest-api/myinfo.service';
import { BoardComponent } from './component/board/board.component';
import { BoardService } from './service/rest-api/board.service';
import { PostComponent } from './component/board/post.component';
import { PostViewComponent } from './component/board/post-view.component';
import { PostModifyComponent } from './component/board/post-modify.component';
import { TranslateModule } from '@ngx-translate/core';
import { AlertDialogComponent } from './component/common/alert-dialog/alert-dialog.component';
import { ConfirmDialogComponent } from './component/common/confirm-dialog/confirm-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SigninComponent,
    SignupComponent,
    LogoutComponent,
    MyinfoComponent,
    BoardComponent,
    PostComponent,
    PostViewComponent,
    PostModifyComponent,
    AlertDialogComponent,
    ConfirmDialogComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    // 외부 api와 통신하기 위해 필요한 HttpClientModule
    HttpClientModule,
    TranslateModule.forRoot()
  ],
  exports:[
    ReactiveFormsModule
  ],
  providers: [
    // 인터셉터 등록 : 인터셉터가 http 요청마다 적용되게 하기 위해서 provider에 추가 
    {
      provide : HTTP_INTERCEPTORS,
      useClass : HttpRequestInterceptorService,
      multi : true,
    }, 
    SignService,
    MyinfoService,
    BoardService
  ],
  // AlertDialogComponent는 시스템 전역에서 사용되므로 최초에 로드되어 있어야한다. 따라서 app.module.ts의 entryComponents항목에 설정해야한다. 
  entryComponents :[
    AlertDialogComponent
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }

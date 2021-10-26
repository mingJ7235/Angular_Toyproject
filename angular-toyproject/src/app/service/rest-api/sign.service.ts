import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponseSingle } from 'src/app/model/common/ApiResponseSingle';
import { DialogService } from '../dialog/dialog.service';
import { ApiValidationService } from './common/api-validation.service';

/**
 * API 통신이 성공하면 결과의 token을 브라우저의 localstorage에 저장하도록 한다. 
 * 이 token을 이용하여 다른 api를 사용할 수 있다. 
 */
@Injectable({
  providedIn: 'root'
})
export class SignService {

  private signInUrl = '/api/v1/signin';
  private signUpUrl = '/api/v1/signup';


  constructor(
    private http: HttpClient,
    private apiValidationService: ApiValidationService,
    private dialogService : DialogService
    ) { }

  //로그인 api 연동
  signIn (id: string, password: string): Promise<any> {
    const params = new FormData();
    params.append('id', id);
    params.append('password', password);
    return this.http.post<ApiResponseSingle> (this.signInUrl, params)
      .toPromise()
      .then (this.apiValidationService.validateResponse)
      .then (response => {
        localStorage.setItem('x-auth-token', response.data);
      })
      .catch(response =>{
        this.dialogService.alert('[로그인 실패]\n', response.error.msg);
        return Promise.reject(response.error.msg);
      }) 
  }

  //가입 api 연동
  signUp(id: string, password: string, name: string): Promise<any> {
    const params = new FormData();
    params.append('id', id);
    params.append('password', password);
    params.append('name', name);
    return this.http.post<ApiResponseSingle>(this.signUpUrl, params)
      .toPromise()
      .then(this.apiValidationService.validateResponse)
      .then(response => {
        return true;
      })
      .catch(response => {
        this.dialogService.alert('[회원 가입중 오류 발생했습니다.]', response.error.msg);  
        return Promise.reject(response.error.msg);
      });
  }

  isSignIn() : boolean {
    const token = localStorage.getItem('x-auth-token');
    if (token) {
      return true;
    } else {
      return false;
    }
  }


}

import { ApiResponseSingle } from './../../model/common/ApiResponseSingle';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from 'src/app/model/myinfo/User';
import { ApiValidationService } from './common/api-validation.service';


@Injectable({
  providedIn: 'root'
})
export class MyinfoService {

  constructor(
    private http: HttpClient,
    private apiValidationService : ApiValidationService
  ) {}

  private getUserUrl = '/api/v1/user';

  getUser(): Promise<User> {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if(loginUser == null) {
      return this.http.get<ApiResponseSingle>(this.getUserUrl)
        .toPromise()
        .then(this.apiValidationService.validateResponse)
        .then(response => {
          localStorage.setItem('loginUser', JSON.stringify(response.data));
          return response.data as User;
        })
        .catch(response => {
          localStorage.removeItem('x-auth-token');
          alert('[회원 정보 조회중 오류가 발생했습니다.]\n' + response.error.msg);
          return Promise.reject(response.error.msg);
        });
    } else {
      return Promise.resolve(loginUser);
    }
  }
}

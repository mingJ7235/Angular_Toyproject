import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


/**
 * Intercptor를 이용하여 http로 api요청시 자동으로 token을 세팅하도록 처리. 
 * token은 로그인한 이후에 localstorage에 저장이되므로, api마다 매번 파라미터로 받아서 세팅할 필요가없기때문
 */
@Injectable({
  providedIn: 'root'
})
export class HttpRequestInterceptorService implements HttpInterceptor {

  /**
   * Interceptor는 http 요청을 중간에 가로채서 header에 추가 정보를 세팅하는 역할을 한다.
   * localstorage에 저장된 token이 있으면 header에 x-auth-token값을 세팅한다. 
   */
  intercept(req: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
    var token = localStorage.getItem('x-auth-token');
    var reqHeader: HttpHeaders = req.headers;
    if (token) {
      reqHeader = reqHeader.set('x-auth-token', token);
    }
    const newRequest = req.clone({headers: reqHeader});
    return next.handle(newRequest);
  }

  constructor() { }
}

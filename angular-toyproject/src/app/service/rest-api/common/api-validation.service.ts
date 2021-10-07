import { Injectable } from '@angular/core';

/**
 * API 처리 기능 개선
 * 
 * api 연동시 결과는 성공 혹은 실패다. 
 * 성공 / 실패 여부는 json결과의 success: true/false로 알수 있다. 
 * 그리고 실패시 상세 내용은 msg로 확인 가능하다.
 * 이부분은 모둔 api가 공통이므로 component에서 처리하지않고, service에서 공통으로 처리하도록 한다. 
 */

@Injectable({
  providedIn: 'root'
})
export class ApiValidationService {

  public validateResponse (response : any) : Promise<any> {
    if(response.success){
      return Promise.resolve(response);
    } else {
      return Promise.reject(response);
    }
  }
}

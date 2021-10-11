import { ApiValidationService } from './common/api-validation.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  constructor(
    private http: HttpClient,
    private apiValidationService : ApiValidationService
  ) { }

  private getBoardUrl = 'api/v1/board'

  
}

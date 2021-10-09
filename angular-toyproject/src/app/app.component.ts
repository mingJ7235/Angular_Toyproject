import { Component } from '@angular/core';
import { SignService } from './service/rest-api/sign.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular-toyproject';

  constructor (
   public signService : SignService
  ){}
}

import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/myinfo/User';

@Component({
  selector: 'app-myinfo',
  templateUrl: './myinfo.component.html',
  styleUrls: ['./myinfo.component.scss']
})
export class MyinfoComponent implements OnInit {

  loginUser : User;

  constructor() {
    this.loginUser = 
   }

  ngOnInit(): void {
  }

}

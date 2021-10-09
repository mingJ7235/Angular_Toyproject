import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  template: ''
  
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) {
    localStorage.removeItem('x-auth-token');
    localStorage.removeItem('loginUser')
    this.router.navigate(['/']);
  }

  ngOnInit(): void {
  }

}

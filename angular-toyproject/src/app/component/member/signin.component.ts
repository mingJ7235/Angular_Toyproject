import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent{

  signInForm : FormGroup

  constructor() { 
    this.signInForm = new FormGroup ({
      id : new FormControl ('', [Validators.required, Validators.email]),
      password : new FormControl('', [Validators.required])
    });
  }

  get id () {
    return this.signInForm.get('id');
  }

  get password () {
    return this.signInForm.get('password');
  }

  submit() {

  }

  

}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignService } from 'src/app/service/rest-api/sign.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})

export class SignupComponent {

  signUpForm: FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private signService: SignService) {
    this.signUpForm = this.formBuilder.group({
      id: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$') //정규표현식 (Validators.email을 사용해도된다. )
      ])),
      password: new FormControl('', [Validators.required]),
      password_re: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required])
    }, {validator: this.checkPassword});
  }

  checkPassword(group: FormGroup) {
    let password = group.controls.password.value;
    let passwordRe = group.controls.password_re.value;
    return password === '' || passwordRe === '' || password === passwordRe ? null : { notSame : true }
  }

  // form field에 쉽게 접근하기 위해 getter 세팅
  get f() { return this.signUpForm.controls; }

  submit() {
    if(this.signUpForm.valid) {
      this.signService.signUp(this.signUpForm.value.id, this.signUpForm.value.password, this.signUpForm.value.name)
        .then(response =>{
          this.signService.signIn(this.signUpForm.value.id, this.signUpForm.value.password)
            .then(response =>{
              this.router.navigate(['/']);
            })
        })
    }
  }
}

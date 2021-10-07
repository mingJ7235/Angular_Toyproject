import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SignService } from 'src/app/service/rest-api/sign.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit{

  redirectTo !: string;
  signInForm : FormGroup

  constructor(
    private signService : SignService,
    private router: Router,
    private route: ActivatedRoute,

    ) { 
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

  ngOnInit () {
    this.route.queryParams.subscribe(params => {
      this.redirectTo = params ['redirectTo']
    });
  }

  submit() {
    if (this.signInForm.valid) {
      this.signService.signIn(this.signInForm.value.id, this.signInForm.value.password)
        .then (data => {
          this.router.navigate([this.redirectTo ? this.redirectTo : '/'])
        })
    }
  }
}

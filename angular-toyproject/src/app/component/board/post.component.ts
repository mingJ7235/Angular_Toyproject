import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BoardService } from 'src/app/service/rest-api/board.service';
import { MyinfoService } from 'src/app/service/rest-api/myinfo.service';
import { SignService } from 'src/app/service/rest-api/sign.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
/**
 * constructor 에서 postForm에 Validation 설정을 추가한다. (html 파일에서 validation 적용을 위해 form에 formGrup, formControlName을 설정했다.)
 * ActivatedRoute를 이용하여 현재 주소로부터 path variable로 설정된 boardName을 얻는다.
 * form으로부터 필드 정보를 얻기위해 postForm을 getter 처리한다. 이렇게하면 html에서는 'f.필드명'으로 접근하여 필드의 유효성 정보를 확인할 수 있다. 
 */
export class PostComponent implements OnInit {

  boardName : string;
  postForm : FormGroup;

  constructor(
    private router: Router,
    private route : ActivatedRoute,
    private formBuilder : FormBuilder,

    private signService : SignService,
    private myInfoService : MyinfoService,
    private boardService : BoardService

  ) { 
    this.postForm = this.formBuilder.group({
      title: new FormControl('', [Validators.required]),
      content: new FormControl('', [Validators.required])
    });
    this.boardName = this.route.snapshot.params['boardName'];
  }

  // form 필드에 쉽게 접근하기 위해 postForm을 getter 처리함. 
  get f () {
    return this.postForm.controls;
  }

  ngOnInit(): void {
  }

  submit() {
    if(this.signService.isSignIn() && this.postForm.valid) {
      this.myInfoService.getUser().then(user => {
        this.boardService.addPost(
          this.boardName, 
          user.name, 
          this.postForm.value.title,
          this.postForm.value.content)
          .then(response => {
            this.router.navigate(['/board/'+this.boardName]);
          })
      })
    }
  }

}

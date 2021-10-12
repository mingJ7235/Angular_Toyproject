import { BoardService } from './../../service/rest-api/board.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/model/board/Post';

@Component({
  selector: 'app-post-modify',
  templateUrl: './post-modify.component.html',
  styleUrls: ['./post-modify.component.scss']
})
export class PostModifyComponent implements OnInit {

  boardName : string;
  postId : number;
  post = {} as Post;
  postForm : FormGroup;

  constructor(
    private router : Router,
    private route : ActivatedRoute,
    private boardService : BoardService,
    private formBuilder : FormBuilder
  ) {
    this.boardName = this.route.snapshot.params['boardName'];
    this.postId = this.route.snapshot.params['postId'];
    this.postForm = this.formBuilder.group({
      title : new FormControl('', [Validators.required]),
      content : new FormControl('', [Validators.required])
    });
  }

  get f () {
    return this.postForm.controls;
  }

  ngOnInit() {
    this.boardService.viewPost(this.postId)
      .then(post => {
        this.post = post;
      })
  }

  submit() {

  }

}

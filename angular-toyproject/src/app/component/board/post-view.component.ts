import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/model/board/Post';
import { User } from 'src/app/model/myinfo/User';
import { BoardService } from 'src/app/service/rest-api/board.service';
import { MyinfoService } from 'src/app/service/rest-api/myinfo.service';
import { SignService } from 'src/app/service/rest-api/sign.service';

@Component({
  selector: 'app-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.scss']
})
export class PostViewComponent implements OnInit {

  loginUser : User;
  boardName : string;
  postId : number;
  post : Post;

  constructor(
    private route: ActivatedRoute,
    private boardService : BoardService,
    public signService : SignService,
    private myInfoService : MyinfoService) { 
      /**
       * constructor에서 path variable로 넘어온 boardName과 postId를 얻는다. 
       */
      this.boardName = this.route.snapshot.params['boardName'];
      this.postId = this.route.snapshot.params['postId'];
  }
  /**
   * ngOnInit() : 페이지 초기화 완료시 작동하는 함수
   * 페이지 초기화가 완료되면, 현재 로그인한 회원정보와 게시글 상세정보를 조회하여 변수에 세팅
   */
  ngOnInit(): void {
    if(this.signService.isSignIn()) {
      this.myInfoService.getUser()
        .then(user => {
          this.loginUser = user;
        });

    }
    this.boardService.viewPost(this.postId)
      .then(post =>{
        this.post = post;
      })
  }

}

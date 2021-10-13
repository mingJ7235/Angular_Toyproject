import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/model/board/Post';
import { User } from 'src/app/model/myinfo/User';
import { BoardService } from 'src/app/service/rest-api/board.service';
import { MyinfoService } from 'src/app/service/rest-api/myinfo.service';
import { SignService } from 'src/app/service/rest-api/sign.service';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  posts: Post[] = [];
  displayedColumns : string[] = ['postId', 'title', 'author', 'createdAt', 'modifiedAt'];
  boardName: string;
  loginUser : User;
  boardMenu : [];

  /**
   * 게시판 주소로부터 게시판 이름 (boardName)이 Path variable로 넘어오게 되는데,
   * ActivatedRoute를 통해 해당 값을 얻어 낼 수 있다. 
   */
  constructor(
    private boardService: BoardService,
    private route : ActivatedRoute,
    public signService : SignService,
    private myInfoService : MyinfoService,
    private router : Router

    
    ) { 
      this.boardName = this.route.snapshot.params['boardName'];
    }

  ngOnInit(): void {
    this.boardService.getPosts(this.boardName).then(response => {
      this.posts =response;
    });
    if (this.signService.isSignIn()) {
      this.myInfoService.getUser()
        .then(user => {
          this.loginUser = user;
        })
    }
  }

  delete (postId : number) {
    if (confirm('정말 삭제하시겠습니까?')) {
      this.boardService.deletePost(postId)
        .then(response => {
          window.location.reload();
        })
    }
  }

}

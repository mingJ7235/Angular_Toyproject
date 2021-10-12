import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/model/board/Post';
import { BoardService } from 'src/app/service/rest-api/board.service';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  posts: Post[] = [];
  displayedColumns : string[] = ['postId', 'title', 'author', 'createdAt', 'modifiedAt'];
  boardName: string;

  /**
   * 게시판 주소로부터 게시판 이름 (boardName)이 Path variable로 넘어오게 되는데,
   * ActivatedRoute를 통해 해당 값을 얻어 낼 수 있다. 
   */
  constructor(
    private boardService: BoardService,
    private route : ActivatedRoute) { 
      this.boardName = this.route.snapshot.params['boardName'];
    }

  ngOnInit(): void {
    this.boardService.getPosts(this.boardName).then(response => {
      this.posts =response;
    })
  }

}

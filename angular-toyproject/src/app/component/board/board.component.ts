import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/board/Post';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  posts: Post[] = [
      {'postId':3, 'title': '게시판 테스트03', 'author': '유저03', 'content': '테스트03', 'createdAt': new Date(), 'modifiedAt' : new Date(), 'user' : null},
      {'postId':2, 'title': '게시판 테스트02', 'author': '유저02', 'content': '테스트02', 'createdAt': new Date(), 'modifiedAt' : new Date(), 'user' : null},
      {'postId':1, 'title': '게시판 테스트01', 'author': '유저01', 'content': '테스트01', 'createdAt': new Date(), 'modifiedAt' : new Date(), 'user' : null}
  ];

  headerColumns: string[] = ['postId', 'title', 'author', 'createdAt', 'modifiedAt'];

  constructor() { }

  ngOnInit(): void {
  }

}

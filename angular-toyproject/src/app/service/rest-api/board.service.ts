import { ApiValidationService } from './common/api-validation.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from 'src/app/model/board/Post';
import { ApiResponseList } from 'src/app/model/common/ApiResponseList';
import { ApiResponseSingle } from 'src/app/model/common/ApiResponseSingle';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  constructor(
    private http: HttpClient,
    private apiValidationService : ApiValidationService
  ) { };

  private getBoardUrl = '/api/v1/board'

  //게시물 리스트 가져오기
  getPosts(boardName : string) : Promise<Post[]> {
    const getPostsUrl = this.getBoardUrl + '/' + boardName + '/posts';

    return this.http.get<ApiResponseList> (getPostsUrl)
      .toPromise()
      .then(this.apiValidationService.validateResponse)
      .then(response => {
        return response.list as Post[];
      })
      .catch (response =>{
        alert('[게시판 조회 중 오류 발생]n' + response.error.msg);
        return Promise.reject(response.error.msg);
      })
  }

  //게시물 등록
  addPost (boardName: string, author: string, title: string, content: string) : Promise<Post> {
    const postUrl = this.getBoardUrl + '/' + boardName + '/post';
    const params = new FormData();
    params.append('author', author);
    params.append('title', title);
    params.append('content', content);

    return this.http.post<ApiResponseSingle> (postUrl, params)
      .toPromise()
      .then(this.apiValidationService.validateResponse)
      .then(response => {
        return response.data as Post;
      })
      .catch(response => {
        alert('[게시글 등록 중 오류 발생]\n' + response.error.msg);
        return Promise.reject(response.error.msg);
      });
  }

  viewPost (postId: number) : Promise<Post> {
    const getPostUrl = this.getBoardUrl +'/post/' + postId;

    return this.http.get<ApiResponseSingle> (getPostUrl) 
      .toPromise()
      .then(this.apiValidationService.validateResponse)
      .then(response => {

        return response.data as Post;
      })
      .catch (response => {
        alert('[게시글 조회 중 오류발생]\n' + response.error.msg);
        return Promise.reject(response.error.msg);
      })
  }


}

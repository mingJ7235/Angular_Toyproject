import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { Post } from "src/app/model/board/Post";
import { BoardService } from "src/app/service/rest-api/board.service";

/**
 * 주소의 PathVariable로 부터 boardName을 받아 게시글 리스트를 반환하는 resolve 메서드를 구현
 */
@Injectable()
export class BoardResolve implements Resolve<Post[]> {
    constructor(
        private boardService : BoardService
    ) {}

    resolve(
        route : ActivatedRouteSnapshot, 
        state: RouterStateSnapshot) : Post[] | Observable<Post[]> | Promise <Post[]> {
            return this.boardService.getPosts(route.params['boardName']);
    }
}
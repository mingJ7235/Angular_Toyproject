import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AlertDialogComponent } from 'src/app/component/common/alert-dialog/alert-dialog.component';

@Injectable({
  providedIn: 'root'
})

/**
 * 커스텀 다이얼로그를 여러 컴포넌트에서 재사용할 수 있도록 서비스를 생성.
 * 
 * 제목과 내용을 인자로 받는 alert 메서드를 생성
 * dialog.open을 통해 AertDialogComponent를 화면에 출력한다. 
 * 
 * 가로사이즈와 modal 여부 (diableClose), 전달할 데이터를 세팅
 * 메서드 결과로 Dialog Reference를 리턴하여 확인 버튼을 눌렀을 때의 이벤트를 처리할 수잇게끔 한다. 
 */
export class DialogService {

  constructor(private dialog : MatDialog) { }

  alert(title: string, desc : string) : any{
    const dialogRef = this.dialog.open(AlertDialogComponent, {
      width: '300px',
      disableClose: true,
      data : {
        title : title,
        description : desc
      }
    })
    return dialogRef;
  }
}

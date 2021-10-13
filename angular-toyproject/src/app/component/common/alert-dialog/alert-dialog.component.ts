import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-alert-dialog',
  templateUrl: './alert-dialog.component.html',
  styleUrls: ['./alert-dialog.component.scss']
})
export class AlertDialogComponent implements OnInit {

  form : FormGroup;
  title : string;
  description : string;

  /**
   * 다이얼로그를 호출하는 쪽에서 data를 세팅해서 보내면 constructor 에서 @Inject(MAT_DIALOG_DATA)로 데이터를 읽을 수 있다. 
   * constructor에서 읽은 정보는 다이얼로그 화면이 그려지면 즉, ngOnInit()가 실행되면, 제목과 내용이 세팅되어 화면에 출력된다. 
   */
  constructor(
    private fb : FormBuilder,
    private dialogRef : MatDialogRef<AlertDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data) {
      this.title = data.title;
      this.description = data.description;
    }

  ngOnInit(): void {
    this.form = this.fb.group({
      title: [this.title, []],
      description: [this.description, []]
    });
  }

}

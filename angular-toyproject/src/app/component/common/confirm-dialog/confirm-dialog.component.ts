import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

  form : FormGroup;
  title : string;
  description : string;

  constructor(
    private fb : FormBuilder,
    private dialogRef : MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.title = data.title;
    this.description = data.description;
  }

  ngOnInit() {
    this.form = this.fb.group({
      title : [this.title, []],
      description : [this.description, []]
    })
  }

  //취소버튼 처리할 close() 메서드 
  close () {
    this.dialogRef.close();
  }

}

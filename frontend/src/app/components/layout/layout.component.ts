import { Component } from '@angular/core';
import {Router} from '@angular/router';
import { NzMessageService } from "ng-zorro-antd/message";

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.less']
})
export class LayoutComponent {
  logged: boolean = false;
  username: string;
  constructor(private router: Router, private message: NzMessageService) { }

  ngOnInit(): void {
    if(localStorage.getItem('token')){
      this.logged = true
      this.username = localStorage.getItem('username')
    }
  }

  exit(): void {
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    this.router.navigate(['/login']);
    this.message.success('退出成功')
  }
}

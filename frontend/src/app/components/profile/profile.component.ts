import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  username: string;
  gameNum: number;
  stepNum: number;
  constructor(private activatedRouter: ActivatedRoute) {
    this.username = this.activatedRouter.queryParams["_value"]["username"];
  }

  ngOnInit(): void {
  }

  updateGameNum(e){
    this.gameNum = e.gameNum;
    this.stepNum = e.stepNum;
  }
}

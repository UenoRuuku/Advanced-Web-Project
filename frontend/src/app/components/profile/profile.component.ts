import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { HttpClient } from "@angular/common/http";

type HanoiAllHistoryResponse = [{
  firstTower: string;
  gameId: number;
  id: number;
  operationId: number;
  secondTower: string;
  thirdTower: string;
}];

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.css"],
})
export class ProfileComponent implements OnInit {
  username: string;
  gameNum: number;
  stepNum: number;
  hanoiHistoryTitle: string = `历史游戏详情(${localStorage.getItem("gameDetailId")})`;

  showHanoiHistory: boolean = false;

  constructor(
    private activatedRouter: ActivatedRoute,
    private http: HttpClient
  ) {
    this.username = this.activatedRouter.queryParams["_value"]["username"];
  }

  ngOnInit(): void {}

  updateGameNum(e) {
    this.gameNum = e.gameNum;
    this.stepNum = e.stepNum;
  }

  wantHanoiCallback(event): void {
    this.showHanoiHistory = !this.showHanoiHistory;
  }
}

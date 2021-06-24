import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";

class GameHistory {
  id: number;
  startTime: string;
  endTime: string;
  stepNum: number;
}

@Component({
  selector: "app-game-history",
  templateUrl: "./game-history.component.html",
  styleUrls: ["./game-history.component.css"],
})
export class GameHistoryComponent implements OnInit {
  @Output() private outer = new EventEmitter;
  @Output('wantHanoi') wantHanoi = new EventEmitter<any>();
  
  gameHistoryData: [];
  username: string;
  gameHistoryDataLoading: boolean;
  constructor(
    private http: HttpClient,
    private activatedRouter: ActivatedRoute
  ) {
    this.gameHistoryDataLoading = true;
    this.username = this.activatedRouter.queryParams["_value"]["username"];
  }

  ngOnInit(): void {
    this.http.get(`/history/${this.username}`).subscribe(
      <array>(val) => {
        let stepNum = 0;
        for(let i=0; i<val.length;i++){
          if(val[i].startTime) val[i].startTime = moment(new Date(val[i].startTime)).format("YYYY-MM-DD hh:mm A");
          if(val[i].endTime) val[i].endTime = moment(new Date(val[i].endTime)).format("YYYY-MM-DD hh:mm A");
          stepNum+=val[i].stepNum;
        }
        this.gameHistoryData = val;
        let event = {
          gameNum: this.gameHistoryData.length,
          stepNum: stepNum,
        }
        this.outer.emit(event);
        this.gameHistoryDataLoading = false;
      },
      (error) => {
        console.log("error", error);
        this.gameHistoryDataLoading = false;
      },
      () => {
        console.log("completed");
        this.gameHistoryDataLoading = false;
      }
    );
  }

  wantHanoiCallback(id): void{
    localStorage.setItem("gameDetailId", id);
    this.wantHanoi.emit(id);
  }
}

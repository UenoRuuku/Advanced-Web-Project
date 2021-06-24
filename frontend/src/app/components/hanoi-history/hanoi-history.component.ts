import { Component, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { DomSanitizer } from '@angular/platform-browser';

type HanoiAllHistory = [
  {
    firstTower: string;
    gameId: number;
    id: number;
    operationId: number;
    secondTower: string;
    thirdTower: string;
  }
];

@Component({
  selector: "app-hanoi-history",
  templateUrl: "./hanoi-history.component.html",
  styleUrls: ["./hanoi-history.component.css"],
})
export class HanoiHistoryComponent implements OnInit {
  hanoiHistoryDataLoading: boolean = false;
  hanoiHistoryData: [];

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) {}

  getPlateHtml(i): string {
    const plate1 = `<nz-tag [nzColor]="'#f50'">1</nz-tag>`;
    const plate2 = `<nz-tag [nzColor]="'#2db7f5'">2</nz-tag>`;
    const plate3 = `<nz-tag [nzColor]="'#87d068'">3</nz-tag>`;
    const plate4 = `<nz-tag [nzColor]="'#108ee9'">4</nz-tag>`;
    switch (i) {
      case "1":
        return plate1;
      case "2":
        return plate2;
      case "3":
        return plate3;
      case "4":
        return plate4;
      default:
        return "";
    }
  }

  ngOnInit(): void {
    let gameId = localStorage.getItem("gameDetailId");
    this.http.get(`/history/hanoi/all/${gameId}`).subscribe(<array>(val) => {
      console.log(val);
      for(let i=0; i<val.length;i++){
        val[i].firstTowerHtml = "";
        val[i].secondTowerHtml = "";
        val[i].thirdTowerHtml = "";
        for (let j = 0; j < val[i].firstTower.length; j++) {
          val[i].firstTowerHtml += this.getPlateHtml(val[i].firstTower[j]);
        }
        val[i].firstTowerHtml = this.sanitizer.bypassSecurityTrustHtml(val[i].firstTowerHtml);
        for (let j = 0; j < val[i].secondTower.length; j++) {
          val[i].secondTowerHtml += this.getPlateHtml(val[i].secondTower[j]);
        }
        val[i].secondTowerHtml = this.sanitizer.bypassSecurityTrustHtml(val[i].secondTowerHtml);
        for (let j = 0; j < val[i].thirdTower.length; j++) {
          val[i].thirdTowerHtml += this.getPlateHtml(val[i].thirdTower[j]);
        }
        val[i].thirdTowerHtml = this.sanitizer.bypassSecurityTrustHtml(val[i].thirdTowerHtml);
        if(val[i].firstTowerHtml === "") val[i].firstTowerHtml = "空";
        if(val[i].secondTowerHtml === "") val[i].secondTowerHtml = "空";
        if(val[i].thirdTowerHtml === "") val[i].thirdTowerHtml = "空";
      }
      this.hanoiHistoryData = val;
    });
    // this.http.get(`/history/hanoi/operation/chara/2`).subscribe(
    //   (val)=>{
    //     console.log(val)
    //   }
    // )
  }
}

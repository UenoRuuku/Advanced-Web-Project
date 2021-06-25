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

  containPlate(tower, i){
    if(tower.search(i)!=-1) return true;
    else return false;
  }

  isNullOrIllegal(tower){
    if(tower.search(1)===-1&&tower.search(2)===-1&&tower.search(3)===-1&&tower.search(4)===-1) return true;
    else return false
  }

  ngOnInit(): void {
    let gameId = localStorage.getItem("gameDetailId");
    this.http.get(`/history/hanoi/all/${gameId}`).subscribe(<array>(val) => {
      console.log(val);
      this.hanoiHistoryData = val;
    });
    // this.http.get(`/history/hanoi/operation/chara/2`).subscribe(
    //   (val)=>{
    //     console.log(val)
    //   }
    // )
  }
}

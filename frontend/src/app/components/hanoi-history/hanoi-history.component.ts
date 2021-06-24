import { Component, OnInit } from '@angular/core';

type HanoiAllHistory = [{
  firstTower: string;
  gameId: number;
  id: number;
  operationId: number;
  secondTower: string;
  thirdTower: string;
}];

@Component({
  selector: 'app-hanoi-history',
  templateUrl: './hanoi-history.component.html',
  styleUrls: ['./hanoi-history.component.css']
})
export class HanoiHistoryComponent implements OnInit {
  hanoiAllHistory: HanoiAllHistory;
  constructor() { }

  ngOnInit(): void {
  }

}

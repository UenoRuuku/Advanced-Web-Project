import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HanoiHistoryComponent } from './hanoi-history.component';

describe('HanoiHistoryComponent', () => {
  let component: HanoiHistoryComponent;
  let fixture: ComponentFixture<HanoiHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HanoiHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HanoiHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

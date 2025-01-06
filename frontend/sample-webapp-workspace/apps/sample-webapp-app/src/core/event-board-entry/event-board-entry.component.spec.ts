import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EventBoardEntryComponent } from './event-board-entry.component';

describe('EventBoardEntryComponentComponent', () => {
  let component: EventBoardEntryComponent;
  let fixture: ComponentFixture<EventBoardEntryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventBoardEntryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EventBoardEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

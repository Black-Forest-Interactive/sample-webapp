import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EventBoardComponent } from './event-board.component';

describe('EventBoardComponentComponent', () => {
  let component: EventBoardComponent;
  let fixture: ComponentFixture<EventBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventBoardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EventBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

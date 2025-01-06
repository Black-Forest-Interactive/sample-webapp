import {Component, input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Event} from '@sample-webapp-workspace/core'
import {MatDivider} from "@angular/material/divider";
import {MatCard} from "@angular/material/card";

@Component({
  selector: 'app-event-board-entry',
  imports: [CommonModule, MatDivider, MatCard],
  templateUrl: './event-board-entry.component.html',
  styleUrl: './event-board-entry.component.scss',
})
export class EventBoardEntryComponent {
  event = input.required<Event>()
}

import {Component, computed, resource, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EventService, toPromise} from "@sample-webapp-workspace/core";
import {EventBoardEntryComponent} from "../event-board-entry/event-board-entry.component";

@Component({
  selector: 'app-event-board',
  imports: [CommonModule, EventBoardEntryComponent],
  templateUrl: './event-board.component.html',
  styleUrl: './event-board.component.scss',
})
export class EventBoardComponent {

  pageSize = signal<number>(20)
  pageIndex = signal<number>(0)


  eventsCriteria = computed(() => ({
    pageSize: this.pageSize(),
    pageIndex: this.pageIndex()
  }))


  eventsResource = resource({
    request: this.eventsCriteria,
    loader: (param) => {
      return toPromise(this.service.getAllEvents(param.request.pageIndex, param.request.pageSize), param.abortSignal)
    }
  })

  reloading = this.eventsResource.isLoading
  totalSize = computed(() => this.eventsResource.value()?.totalSize ?? 0)
  events = computed(() => this.eventsResource.value()?.content ?? [])

  constructor(private service: EventService) {
  }

}

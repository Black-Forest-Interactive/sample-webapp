import {Account, AccountInfo} from "@sample-webapp-workspace/core";

export interface Event {
  id: number,
  owner: AccountInfo,
  start: string,
  finish: string,

  title: string,
  shortText: string,
  longText: string,
  imageUrl: string,
  iconUrl: string,

  published: boolean,

  tags: string[]
}

export interface EventInfo {
  event: Event,
  canEdit: boolean
}


export class EventChangeRequest {
  constructor(
    public start: string,
    public finish: string,
    public title: string,
    public shortText: string,
    public longText: string,
    public imageUrl: string,
    public iconUrl: string,
    public categoryIds: number[],
    public published: boolean,
    public tags: string[]
  ) {
  }
}

export class EventCreateRequest {
  constructor(
    public account: Account,
    public content: EventChangeRequest
  ) {
  }
}

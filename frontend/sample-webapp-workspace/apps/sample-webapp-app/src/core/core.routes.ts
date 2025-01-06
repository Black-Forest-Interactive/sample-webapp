import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
    children: [
      {path: '', redirectTo: 'event', pathMatch: "full"},
      {
        path: 'event',
        loadComponent: () => import('./event-board/event-board.component').then(m => m.EventBoardComponent)
      }
    ]
  },
];

import {Route} from '@angular/router';

export const appRoutes: Route[] = [
  {path: '', pathMatch: 'full', redirectTo: 'core'},
  {
    path: 'core',
    loadChildren: () => import('../core/core.routes').then(m => m.routes),
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkRequest, WorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestService } from './work-request.service';
import { WorkRequestComponent } from './work-request.component';
import { WorkRequestDetailComponent } from './work-request-detail.component';
import { WorkRequestUpdateComponent } from './work-request-update.component';

@Injectable({ providedIn: 'root' })
export class WorkRequestResolve implements Resolve<IWorkRequest> {
  constructor(private service: WorkRequestService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkRequest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workRequest: HttpResponse<WorkRequest>) => {
          if (workRequest.body) {
            return of(workRequest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkRequest());
  }
}

export const workRequestRoute: Routes = [
  {
    path: '',
    component: WorkRequestComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkRequestDetailComponent,
    resolve: {
      workRequest: WorkRequestResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkRequestUpdateComponent,
    resolve: {
      workRequest: WorkRequestResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkRequestUpdateComponent,
    resolve: {
      workRequest: WorkRequestResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

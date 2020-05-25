import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkRequestStatusChange, WorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';
import { WorkRequestStatusChangeService } from './work-request-status-change.service';
import { WorkRequestStatusChangeComponent } from './work-request-status-change.component';
import { WorkRequestStatusChangeDetailComponent } from './work-request-status-change-detail.component';
import { WorkRequestStatusChangeUpdateComponent } from './work-request-status-change-update.component';

@Injectable({ providedIn: 'root' })
export class WorkRequestStatusChangeResolve implements Resolve<IWorkRequestStatusChange> {
  constructor(private service: WorkRequestStatusChangeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkRequestStatusChange> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workRequestStatusChange: HttpResponse<WorkRequestStatusChange>) => {
          if (workRequestStatusChange.body) {
            return of(workRequestStatusChange.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkRequestStatusChange());
  }
}

export const workRequestStatusChangeRoute: Routes = [
  {
    path: '',
    component: WorkRequestStatusChangeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ahomeApp.workRequestStatusChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkRequestStatusChangeDetailComponent,
    resolve: {
      workRequestStatusChange: WorkRequestStatusChangeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequestStatusChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkRequestStatusChangeUpdateComponent,
    resolve: {
      workRequestStatusChange: WorkRequestStatusChangeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequestStatusChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkRequestStatusChangeUpdateComponent,
    resolve: {
      workRequestStatusChange: WorkRequestStatusChangeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workRequestStatusChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

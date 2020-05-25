import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorker, Worker } from 'app/shared/model/worker.model';
import { WorkerService } from './worker.service';
import { WorkerComponent } from './worker.component';
import { WorkerDetailComponent } from './worker-detail.component';
import { WorkerUpdateComponent } from './worker-update.component';

@Injectable({ providedIn: 'root' })
export class WorkerResolve implements Resolve<IWorker> {
  constructor(private service: WorkerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((worker: HttpResponse<Worker>) => {
          if (worker.body) {
            return of(worker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Worker());
  }
}

export const workerRoute: Routes = [
  {
    path: '',
    component: WorkerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ahomeApp.worker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkerDetailComponent,
    resolve: {
      worker: WorkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.worker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkerUpdateComponent,
    resolve: {
      worker: WorkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.worker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkerUpdateComponent,
    resolve: {
      worker: WorkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.worker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

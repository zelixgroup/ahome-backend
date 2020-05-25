import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWork, Work } from 'app/shared/model/work.model';
import { WorkService } from './work.service';
import { WorkComponent } from './work.component';
import { WorkDetailComponent } from './work-detail.component';
import { WorkUpdateComponent } from './work-update.component';

@Injectable({ providedIn: 'root' })
export class WorkResolve implements Resolve<IWork> {
  constructor(private service: WorkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWork> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((work: HttpResponse<Work>) => {
          if (work.body) {
            return of(work.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Work());
  }
}

export const workRoute: Routes = [
  {
    path: '',
    component: WorkComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ahomeApp.work.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkDetailComponent,
    resolve: {
      work: WorkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.work.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkUpdateComponent,
    resolve: {
      work: WorkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.work.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkUpdateComponent,
    resolve: {
      work: WorkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.work.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

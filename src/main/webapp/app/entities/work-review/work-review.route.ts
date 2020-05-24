import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkReview, WorkReview } from 'app/shared/model/work-review.model';
import { WorkReviewService } from './work-review.service';
import { WorkReviewComponent } from './work-review.component';
import { WorkReviewDetailComponent } from './work-review-detail.component';
import { WorkReviewUpdateComponent } from './work-review-update.component';

@Injectable({ providedIn: 'root' })
export class WorkReviewResolve implements Resolve<IWorkReview> {
  constructor(private service: WorkReviewService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkReview> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workReview: HttpResponse<WorkReview>) => {
          if (workReview.body) {
            return of(workReview.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkReview());
  }
}

export const workReviewRoute: Routes = [
  {
    path: '',
    component: WorkReviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkReviewDetailComponent,
    resolve: {
      workReview: WorkReviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkReviewUpdateComponent,
    resolve: {
      workReview: WorkReviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkReviewUpdateComponent,
    resolve: {
      workReview: WorkReviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

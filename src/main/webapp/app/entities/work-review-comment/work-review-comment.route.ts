import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkReviewComment, WorkReviewComment } from 'app/shared/model/work-review-comment.model';
import { WorkReviewCommentService } from './work-review-comment.service';
import { WorkReviewCommentComponent } from './work-review-comment.component';
import { WorkReviewCommentDetailComponent } from './work-review-comment-detail.component';
import { WorkReviewCommentUpdateComponent } from './work-review-comment-update.component';

@Injectable({ providedIn: 'root' })
export class WorkReviewCommentResolve implements Resolve<IWorkReviewComment> {
  constructor(private service: WorkReviewCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkReviewComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workReviewComment: HttpResponse<WorkReviewComment>) => {
          if (workReviewComment.body) {
            return of(workReviewComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkReviewComment());
  }
}

export const workReviewCommentRoute: Routes = [
  {
    path: '',
    component: WorkReviewCommentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ahomeApp.workReviewComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkReviewCommentDetailComponent,
    resolve: {
      workReviewComment: WorkReviewCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReviewComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkReviewCommentUpdateComponent,
    resolve: {
      workReviewComment: WorkReviewCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReviewComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkReviewCommentUpdateComponent,
    resolve: {
      workReviewComment: WorkReviewCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workReviewComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

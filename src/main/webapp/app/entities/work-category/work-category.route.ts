import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkCategory, WorkCategory } from 'app/shared/model/work-category.model';
import { WorkCategoryService } from './work-category.service';
import { WorkCategoryComponent } from './work-category.component';
import { WorkCategoryDetailComponent } from './work-category-detail.component';
import { WorkCategoryUpdateComponent } from './work-category-update.component';

@Injectable({ providedIn: 'root' })
export class WorkCategoryResolve implements Resolve<IWorkCategory> {
  constructor(private service: WorkCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workCategory: HttpResponse<WorkCategory>) => {
          if (workCategory.body) {
            return of(workCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkCategory());
  }
}

export const workCategoryRoute: Routes = [
  {
    path: '',
    component: WorkCategoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkCategoryDetailComponent,
    resolve: {
      workCategory: WorkCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkCategoryUpdateComponent,
    resolve: {
      workCategory: WorkCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkCategoryUpdateComponent,
    resolve: {
      workCategory: WorkCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.workCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIdDocumentType, IdDocumentType } from 'app/shared/model/id-document-type.model';
import { IdDocumentTypeService } from './id-document-type.service';
import { IdDocumentTypeComponent } from './id-document-type.component';
import { IdDocumentTypeDetailComponent } from './id-document-type-detail.component';
import { IdDocumentTypeUpdateComponent } from './id-document-type-update.component';

@Injectable({ providedIn: 'root' })
export class IdDocumentTypeResolve implements Resolve<IIdDocumentType> {
  constructor(private service: IdDocumentTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdDocumentType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((idDocumentType: HttpResponse<IdDocumentType>) => {
          if (idDocumentType.body) {
            return of(idDocumentType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IdDocumentType());
  }
}

export const idDocumentTypeRoute: Routes = [
  {
    path: '',
    component: IdDocumentTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocumentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IdDocumentTypeDetailComponent,
    resolve: {
      idDocumentType: IdDocumentTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocumentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IdDocumentTypeUpdateComponent,
    resolve: {
      idDocumentType: IdDocumentTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocumentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IdDocumentTypeUpdateComponent,
    resolve: {
      idDocumentType: IdDocumentTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocumentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

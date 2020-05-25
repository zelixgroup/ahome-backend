import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIdDocument, IdDocument } from 'app/shared/model/id-document.model';
import { IdDocumentService } from './id-document.service';
import { IdDocumentComponent } from './id-document.component';
import { IdDocumentDetailComponent } from './id-document-detail.component';
import { IdDocumentUpdateComponent } from './id-document-update.component';

@Injectable({ providedIn: 'root' })
export class IdDocumentResolve implements Resolve<IIdDocument> {
  constructor(private service: IdDocumentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdDocument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((idDocument: HttpResponse<IdDocument>) => {
          if (idDocument.body) {
            return of(idDocument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IdDocument());
  }
}

export const idDocumentRoute: Routes = [
  {
    path: '',
    component: IdDocumentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ahomeApp.idDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IdDocumentDetailComponent,
    resolve: {
      idDocument: IdDocumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IdDocumentUpdateComponent,
    resolve: {
      idDocument: IdDocumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IdDocumentUpdateComponent,
    resolve: {
      idDocument: IdDocumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ahomeApp.idDocument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

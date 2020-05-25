import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'holiday',
        loadChildren: () => import('./holiday/holiday.module').then(m => m.AhomeHolidayModule),
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.AhomeCityModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.AhomeAddressModule),
      },
      {
        path: 'work-category',
        loadChildren: () => import('./work-category/work-category.module').then(m => m.AhomeWorkCategoryModule),
      },
      {
        path: 'work',
        loadChildren: () => import('./work/work.module').then(m => m.AhomeWorkModule),
      },
      {
        path: 'app-user',
        loadChildren: () => import('./app-user/app-user.module').then(m => m.AhomeAppUserModule),
      },
      {
        path: 'work-request',
        loadChildren: () => import('./work-request/work-request.module').then(m => m.AhomeWorkRequestModule),
      },
      {
        path: 'work-review',
        loadChildren: () => import('./work-review/work-review.module').then(m => m.AhomeWorkReviewModule),
      },
      ,
      {
        path: 'id-document-type',
        loadChildren: () => import('./id-document-type/id-document-type.module').then(m => m.AhomeIdDocumentTypeModule),
      },
      {
        path: 'id-document',
        loadChildren: () => import('./id-document/id-document.module').then(m => m.AhomeIdDocumentModule),
      },
      {
        path: 'worker',
        loadChildren: () => import('./worker/worker.module').then(m => m.AhomeWorkerModule),
      },
      {
        path: 'work-request-status-change',
        loadChildren: () =>
          import('./work-request-status-change/work-request-status-change.module').then(m => m.AhomeWorkRequestStatusChangeModule),
      },
      {
        path: 'work-review-comment',
        loadChildren: () => import('./work-review-comment/work-review-comment.module').then(m => m.AhomeWorkReviewCommentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AhomeEntityModule {}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { WorkReviewComponent } from './work-review.component';
import { WorkReviewDetailComponent } from './work-review-detail.component';
import { WorkReviewUpdateComponent } from './work-review-update.component';
import { WorkReviewDeleteDialogComponent } from './work-review-delete-dialog.component';
import { workReviewRoute } from './work-review.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(workReviewRoute)],
  declarations: [WorkReviewComponent, WorkReviewDetailComponent, WorkReviewUpdateComponent, WorkReviewDeleteDialogComponent],
  entryComponents: [WorkReviewDeleteDialogComponent],
})
export class AhomeWorkReviewModule {}

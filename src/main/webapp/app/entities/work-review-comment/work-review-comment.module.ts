import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { WorkReviewCommentComponent } from './work-review-comment.component';
import { WorkReviewCommentDetailComponent } from './work-review-comment-detail.component';
import { WorkReviewCommentUpdateComponent } from './work-review-comment-update.component';
import { WorkReviewCommentDeleteDialogComponent } from './work-review-comment-delete-dialog.component';
import { workReviewCommentRoute } from './work-review-comment.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(workReviewCommentRoute)],
  declarations: [
    WorkReviewCommentComponent,
    WorkReviewCommentDetailComponent,
    WorkReviewCommentUpdateComponent,
    WorkReviewCommentDeleteDialogComponent,
  ],
  entryComponents: [WorkReviewCommentDeleteDialogComponent],
})
export class AhomeWorkReviewCommentModule {}

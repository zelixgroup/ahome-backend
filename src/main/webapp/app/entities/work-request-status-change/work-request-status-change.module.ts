import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { WorkRequestStatusChangeComponent } from './work-request-status-change.component';
import { WorkRequestStatusChangeDetailComponent } from './work-request-status-change-detail.component';
import { WorkRequestStatusChangeUpdateComponent } from './work-request-status-change-update.component';
import { WorkRequestStatusChangeDeleteDialogComponent } from './work-request-status-change-delete-dialog.component';
import { workRequestStatusChangeRoute } from './work-request-status-change.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(workRequestStatusChangeRoute)],
  declarations: [
    WorkRequestStatusChangeComponent,
    WorkRequestStatusChangeDetailComponent,
    WorkRequestStatusChangeUpdateComponent,
    WorkRequestStatusChangeDeleteDialogComponent,
  ],
  entryComponents: [WorkRequestStatusChangeDeleteDialogComponent],
})
export class AhomeWorkRequestStatusChangeModule {}

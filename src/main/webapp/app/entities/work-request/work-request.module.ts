import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { WorkRequestComponent } from './work-request.component';
import { WorkRequestDetailComponent } from './work-request-detail.component';
import { WorkRequestUpdateComponent } from './work-request-update.component';
import { WorkRequestDeleteDialogComponent } from './work-request-delete-dialog.component';
import { workRequestRoute } from './work-request.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(workRequestRoute)],
  declarations: [WorkRequestComponent, WorkRequestDetailComponent, WorkRequestUpdateComponent, WorkRequestDeleteDialogComponent],
  entryComponents: [WorkRequestDeleteDialogComponent],
})
export class AhomeWorkRequestModule {}

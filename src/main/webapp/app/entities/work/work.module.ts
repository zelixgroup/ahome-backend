import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { WorkComponent } from './work.component';
import { WorkDetailComponent } from './work-detail.component';
import { WorkUpdateComponent } from './work-update.component';
import { WorkDeleteDialogComponent } from './work-delete-dialog.component';
import { workRoute } from './work.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(workRoute)],
  declarations: [WorkComponent, WorkDetailComponent, WorkUpdateComponent, WorkDeleteDialogComponent],
  entryComponents: [WorkDeleteDialogComponent],
})
export class AhomeWorkModule {}

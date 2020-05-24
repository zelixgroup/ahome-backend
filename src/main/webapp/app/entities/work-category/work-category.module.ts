import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { WorkCategoryComponent } from './work-category.component';
import { WorkCategoryDetailComponent } from './work-category-detail.component';
import { WorkCategoryUpdateComponent } from './work-category-update.component';
import { WorkCategoryDeleteDialogComponent } from './work-category-delete-dialog.component';
import { workCategoryRoute } from './work-category.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(workCategoryRoute)],
  declarations: [WorkCategoryComponent, WorkCategoryDetailComponent, WorkCategoryUpdateComponent, WorkCategoryDeleteDialogComponent],
  entryComponents: [WorkCategoryDeleteDialogComponent],
})
export class AhomeWorkCategoryModule {}

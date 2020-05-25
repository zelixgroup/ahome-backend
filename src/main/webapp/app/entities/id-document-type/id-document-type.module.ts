import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { IdDocumentTypeComponent } from './id-document-type.component';
import { IdDocumentTypeDetailComponent } from './id-document-type-detail.component';
import { IdDocumentTypeUpdateComponent } from './id-document-type-update.component';
import { IdDocumentTypeDeleteDialogComponent } from './id-document-type-delete-dialog.component';
import { idDocumentTypeRoute } from './id-document-type.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(idDocumentTypeRoute)],
  declarations: [
    IdDocumentTypeComponent,
    IdDocumentTypeDetailComponent,
    IdDocumentTypeUpdateComponent,
    IdDocumentTypeDeleteDialogComponent,
  ],
  entryComponents: [IdDocumentTypeDeleteDialogComponent],
})
export class AhomeIdDocumentTypeModule {}

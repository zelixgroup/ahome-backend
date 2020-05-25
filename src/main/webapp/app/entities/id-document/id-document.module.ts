import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AhomeSharedModule } from 'app/shared/shared.module';
import { IdDocumentComponent } from './id-document.component';
import { IdDocumentDetailComponent } from './id-document-detail.component';
import { IdDocumentUpdateComponent } from './id-document-update.component';
import { IdDocumentDeleteDialogComponent } from './id-document-delete-dialog.component';
import { idDocumentRoute } from './id-document.route';

@NgModule({
  imports: [AhomeSharedModule, RouterModule.forChild(idDocumentRoute)],
  declarations: [IdDocumentComponent, IdDocumentDetailComponent, IdDocumentUpdateComponent, IdDocumentDeleteDialogComponent],
  entryComponents: [IdDocumentDeleteDialogComponent],
})
export class AhomeIdDocumentModule {}

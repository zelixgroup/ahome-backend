import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdDocumentType } from 'app/shared/model/id-document-type.model';
import { IdDocumentTypeService } from './id-document-type.service';

@Component({
  templateUrl: './id-document-type-delete-dialog.component.html',
})
export class IdDocumentTypeDeleteDialogComponent {
  idDocumentType?: IIdDocumentType;

  constructor(
    protected idDocumentTypeService: IdDocumentTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.idDocumentTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('idDocumentTypeListModification');
      this.activeModal.close();
    });
  }
}

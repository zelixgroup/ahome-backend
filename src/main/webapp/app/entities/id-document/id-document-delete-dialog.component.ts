import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdDocument } from 'app/shared/model/id-document.model';
import { IdDocumentService } from './id-document.service';

@Component({
  templateUrl: './id-document-delete-dialog.component.html',
})
export class IdDocumentDeleteDialogComponent {
  idDocument?: IIdDocument;

  constructor(
    protected idDocumentService: IdDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.idDocumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('idDocumentListModification');
      this.activeModal.close();
    });
  }
}

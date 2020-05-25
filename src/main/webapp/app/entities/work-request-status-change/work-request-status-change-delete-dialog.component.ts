import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';
import { WorkRequestStatusChangeService } from './work-request-status-change.service';

@Component({
  templateUrl: './work-request-status-change-delete-dialog.component.html',
})
export class WorkRequestStatusChangeDeleteDialogComponent {
  workRequestStatusChange?: IWorkRequestStatusChange;

  constructor(
    protected workRequestStatusChangeService: WorkRequestStatusChangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workRequestStatusChangeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workRequestStatusChangeListModification');
      this.activeModal.close();
    });
  }
}

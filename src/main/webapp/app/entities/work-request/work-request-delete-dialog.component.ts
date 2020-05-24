import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestService } from './work-request.service';

@Component({
  templateUrl: './work-request-delete-dialog.component.html',
})
export class WorkRequestDeleteDialogComponent {
  workRequest?: IWorkRequest;

  constructor(
    protected workRequestService: WorkRequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workRequestService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workRequestListModification');
      this.activeModal.close();
    });
  }
}

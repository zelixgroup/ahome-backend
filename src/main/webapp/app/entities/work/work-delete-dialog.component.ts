import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWork } from 'app/shared/model/work.model';
import { WorkService } from './work.service';

@Component({
  templateUrl: './work-delete-dialog.component.html',
})
export class WorkDeleteDialogComponent {
  work?: IWork;

  constructor(protected workService: WorkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workListModification');
      this.activeModal.close();
    });
  }
}

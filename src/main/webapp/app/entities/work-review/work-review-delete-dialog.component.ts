import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkReview } from 'app/shared/model/work-review.model';
import { WorkReviewService } from './work-review.service';

@Component({
  templateUrl: './work-review-delete-dialog.component.html',
})
export class WorkReviewDeleteDialogComponent {
  workReview?: IWorkReview;

  constructor(
    protected workReviewService: WorkReviewService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workReviewService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workReviewListModification');
      this.activeModal.close();
    });
  }
}

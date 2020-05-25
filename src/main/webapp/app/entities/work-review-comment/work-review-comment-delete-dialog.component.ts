import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkReviewComment } from 'app/shared/model/work-review-comment.model';
import { WorkReviewCommentService } from './work-review-comment.service';

@Component({
  templateUrl: './work-review-comment-delete-dialog.component.html',
})
export class WorkReviewCommentDeleteDialogComponent {
  workReviewComment?: IWorkReviewComment;

  constructor(
    protected workReviewCommentService: WorkReviewCommentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workReviewCommentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workReviewCommentListModification');
      this.activeModal.close();
    });
  }
}

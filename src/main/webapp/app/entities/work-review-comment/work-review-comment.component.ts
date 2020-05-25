import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkReviewComment } from 'app/shared/model/work-review-comment.model';
import { WorkReviewCommentService } from './work-review-comment.service';
import { WorkReviewCommentDeleteDialogComponent } from './work-review-comment-delete-dialog.component';

@Component({
  selector: 'jhi-work-review-comment',
  templateUrl: './work-review-comment.component.html',
})
export class WorkReviewCommentComponent implements OnInit, OnDestroy {
  workReviewComments?: IWorkReviewComment[];
  eventSubscriber?: Subscription;

  constructor(
    protected workReviewCommentService: WorkReviewCommentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workReviewCommentService
      .query()
      .subscribe((res: HttpResponse<IWorkReviewComment[]>) => (this.workReviewComments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkReviewComments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkReviewComment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkReviewComments(): void {
    this.eventSubscriber = this.eventManager.subscribe('workReviewCommentListModification', () => this.loadAll());
  }

  delete(workReviewComment: IWorkReviewComment): void {
    const modalRef = this.modalService.open(WorkReviewCommentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workReviewComment = workReviewComment;
  }
}

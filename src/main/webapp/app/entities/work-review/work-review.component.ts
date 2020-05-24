import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkReview } from 'app/shared/model/work-review.model';
import { WorkReviewService } from './work-review.service';
import { WorkReviewDeleteDialogComponent } from './work-review-delete-dialog.component';

@Component({
  selector: 'jhi-work-review',
  templateUrl: './work-review.component.html',
})
export class WorkReviewComponent implements OnInit, OnDestroy {
  workReviews?: IWorkReview[];
  eventSubscriber?: Subscription;

  constructor(protected workReviewService: WorkReviewService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.workReviewService.query().subscribe((res: HttpResponse<IWorkReview[]>) => (this.workReviews = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkReviews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkReview): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkReviews(): void {
    this.eventSubscriber = this.eventManager.subscribe('workReviewListModification', () => this.loadAll());
  }

  delete(workReview: IWorkReview): void {
    const modalRef = this.modalService.open(WorkReviewDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workReview = workReview;
  }
}

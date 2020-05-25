import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';
import { WorkRequestStatusChangeService } from './work-request-status-change.service';
import { WorkRequestStatusChangeDeleteDialogComponent } from './work-request-status-change-delete-dialog.component';

@Component({
  selector: 'jhi-work-request-status-change',
  templateUrl: './work-request-status-change.component.html',
})
export class WorkRequestStatusChangeComponent implements OnInit, OnDestroy {
  workRequestStatusChanges?: IWorkRequestStatusChange[];
  eventSubscriber?: Subscription;

  constructor(
    protected workRequestStatusChangeService: WorkRequestStatusChangeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workRequestStatusChangeService
      .query()
      .subscribe((res: HttpResponse<IWorkRequestStatusChange[]>) => (this.workRequestStatusChanges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkRequestStatusChanges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkRequestStatusChange): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkRequestStatusChanges(): void {
    this.eventSubscriber = this.eventManager.subscribe('workRequestStatusChangeListModification', () => this.loadAll());
  }

  delete(workRequestStatusChange: IWorkRequestStatusChange): void {
    const modalRef = this.modalService.open(WorkRequestStatusChangeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workRequestStatusChange = workRequestStatusChange;
  }
}

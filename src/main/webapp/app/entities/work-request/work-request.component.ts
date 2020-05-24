import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestService } from './work-request.service';
import { WorkRequestDeleteDialogComponent } from './work-request-delete-dialog.component';

@Component({
  selector: 'jhi-work-request',
  templateUrl: './work-request.component.html',
})
export class WorkRequestComponent implements OnInit, OnDestroy {
  workRequests?: IWorkRequest[];
  eventSubscriber?: Subscription;

  constructor(
    protected workRequestService: WorkRequestService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workRequestService.query().subscribe((res: HttpResponse<IWorkRequest[]>) => (this.workRequests = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkRequests();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkRequest): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkRequests(): void {
    this.eventSubscriber = this.eventManager.subscribe('workRequestListModification', () => this.loadAll());
  }

  delete(workRequest: IWorkRequest): void {
    const modalRef = this.modalService.open(WorkRequestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workRequest = workRequest;
  }
}

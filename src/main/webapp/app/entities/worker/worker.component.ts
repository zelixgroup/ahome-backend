import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from './worker.service';
import { WorkerDeleteDialogComponent } from './worker-delete-dialog.component';

@Component({
  selector: 'jhi-worker',
  templateUrl: './worker.component.html',
})
export class WorkerComponent implements OnInit, OnDestroy {
  workers?: IWorker[];
  eventSubscriber?: Subscription;

  constructor(
    protected workerService: WorkerService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInWorkers(): void {
    this.eventSubscriber = this.eventManager.subscribe('workerListModification', () => this.loadAll());
  }

  delete(worker: IWorker): void {
    const modalRef = this.modalService.open(WorkerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.worker = worker;
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWork } from 'app/shared/model/work.model';
import { WorkService } from './work.service';
import { WorkDeleteDialogComponent } from './work-delete-dialog.component';

@Component({
  selector: 'jhi-work',
  templateUrl: './work.component.html',
})
export class WorkComponent implements OnInit, OnDestroy {
  works?: IWork[];
  eventSubscriber?: Subscription;

  constructor(protected workService: WorkService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.workService.query().subscribe((res: HttpResponse<IWork[]>) => (this.works = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWork): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorks(): void {
    this.eventSubscriber = this.eventManager.subscribe('workListModification', () => this.loadAll());
  }

  delete(work: IWork): void {
    const modalRef = this.modalService.open(WorkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.work = work;
  }
}

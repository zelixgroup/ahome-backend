import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkCategory } from 'app/shared/model/work-category.model';
import { WorkCategoryService } from './work-category.service';
import { WorkCategoryDeleteDialogComponent } from './work-category-delete-dialog.component';

@Component({
  selector: 'jhi-work-category',
  templateUrl: './work-category.component.html',
})
export class WorkCategoryComponent implements OnInit, OnDestroy {
  workCategories?: IWorkCategory[];
  eventSubscriber?: Subscription;

  constructor(
    protected workCategoryService: WorkCategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workCategoryService.query().subscribe((res: HttpResponse<IWorkCategory[]>) => (this.workCategories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('workCategoryListModification', () => this.loadAll());
  }

  delete(workCategory: IWorkCategory): void {
    const modalRef = this.modalService.open(WorkCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workCategory = workCategory;
  }
}

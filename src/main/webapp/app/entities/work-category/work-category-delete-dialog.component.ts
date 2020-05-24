import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkCategory } from 'app/shared/model/work-category.model';
import { WorkCategoryService } from './work-category.service';

@Component({
  templateUrl: './work-category-delete-dialog.component.html',
})
export class WorkCategoryDeleteDialogComponent {
  workCategory?: IWorkCategory;

  constructor(
    protected workCategoryService: WorkCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workCategoryListModification');
      this.activeModal.close();
    });
  }
}

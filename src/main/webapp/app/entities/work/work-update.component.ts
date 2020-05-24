import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWork, Work } from 'app/shared/model/work.model';
import { WorkService } from './work.service';
import { IWorkCategory } from 'app/shared/model/work-category.model';
import { WorkCategoryService } from 'app/entities/work-category/work-category.service';

@Component({
  selector: 'jhi-work-update',
  templateUrl: './work-update.component.html',
})
export class WorkUpdateComponent implements OnInit {
  isSaving = false;
  workcategories: IWorkCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    picture: [],
    icone: [],
    pricePerHour: [],
    category: [],
  });

  constructor(
    protected workService: WorkService,
    protected workCategoryService: WorkCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ work }) => {
      this.updateForm(work);

      this.workCategoryService.query().subscribe((res: HttpResponse<IWorkCategory[]>) => (this.workcategories = res.body || []));
    });
  }

  updateForm(work: IWork): void {
    this.editForm.patchValue({
      id: work.id,
      name: work.name,
      description: work.description,
      picture: work.picture,
      icone: work.icone,
      pricePerHour: work.pricePerHour,
      category: work.category,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const work = this.createFromForm();
    if (work.id !== undefined) {
      this.subscribeToSaveResponse(this.workService.update(work));
    } else {
      this.subscribeToSaveResponse(this.workService.create(work));
    }
  }

  private createFromForm(): IWork {
    return {
      ...new Work(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      icone: this.editForm.get(['icone'])!.value,
      pricePerHour: this.editForm.get(['pricePerHour'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWork>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IWorkCategory): any {
    return item.id;
  }
}

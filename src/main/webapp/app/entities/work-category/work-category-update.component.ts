import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkCategory, WorkCategory } from 'app/shared/model/work-category.model';
import { WorkCategoryService } from './work-category.service';

@Component({
  selector: 'jhi-work-category-update',
  templateUrl: './work-category-update.component.html',
})
export class WorkCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    picture: [],
    icone: [],
    pricePerHour: [],
  });

  constructor(protected workCategoryService: WorkCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workCategory }) => {
      this.updateForm(workCategory);
    });
  }

  updateForm(workCategory: IWorkCategory): void {
    this.editForm.patchValue({
      id: workCategory.id,
      name: workCategory.name,
      description: workCategory.description,
      picture: workCategory.picture,
      icone: workCategory.icone,
      pricePerHour: workCategory.pricePerHour,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workCategory = this.createFromForm();
    if (workCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.workCategoryService.update(workCategory));
    } else {
      this.subscribeToSaveResponse(this.workCategoryService.create(workCategory));
    }
  }

  private createFromForm(): IWorkCategory {
    return {
      ...new WorkCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      icone: this.editForm.get(['icone'])!.value,
      pricePerHour: this.editForm.get(['pricePerHour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkCategory>>): void {
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
}

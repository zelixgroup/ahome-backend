import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdDocumentType, IdDocumentType } from 'app/shared/model/id-document-type.model';
import { IdDocumentTypeService } from './id-document-type.service';

@Component({
  selector: 'jhi-id-document-type-update',
  templateUrl: './id-document-type-update.component.html',
})
export class IdDocumentTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [],
    description: [],
    needVerso: [],
  });

  constructor(protected idDocumentTypeService: IdDocumentTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idDocumentType }) => {
      this.updateForm(idDocumentType);
    });
  }

  updateForm(idDocumentType: IIdDocumentType): void {
    this.editForm.patchValue({
      id: idDocumentType.id,
      label: idDocumentType.label,
      description: idDocumentType.description,
      needVerso: idDocumentType.needVerso,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const idDocumentType = this.createFromForm();
    if (idDocumentType.id !== undefined) {
      this.subscribeToSaveResponse(this.idDocumentTypeService.update(idDocumentType));
    } else {
      this.subscribeToSaveResponse(this.idDocumentTypeService.create(idDocumentType));
    }
  }

  private createFromForm(): IIdDocumentType {
    return {
      ...new IdDocumentType(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      description: this.editForm.get(['description'])!.value,
      needVerso: this.editForm.get(['needVerso'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdDocumentType>>): void {
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

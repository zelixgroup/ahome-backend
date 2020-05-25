import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IIdDocument, IdDocument } from 'app/shared/model/id-document.model';
import { IdDocumentService } from './id-document.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IIdDocumentType } from 'app/shared/model/id-document-type.model';
import { IdDocumentTypeService } from 'app/entities/id-document-type/id-document-type.service';
import { IWorker } from 'app/shared/model/worker.model';
import { WorkerService } from 'app/entities/worker/worker.service';

type SelectableEntity = IIdDocumentType | IWorker;

@Component({
  selector: 'jhi-id-document-update',
  templateUrl: './id-document-update.component.html',
})
export class IdDocumentUpdateComponent implements OnInit {
  isSaving = false;
  iddocumenttypes: IIdDocumentType[] = [];
  workers: IWorker[] = [];

  editForm = this.fb.group({
    id: [],
    idDocumentRecto: [],
    idDocumentRectoContentType: [],
    idDocumentVerso: [],
    idDocumentVersoContentType: [],
    type: [],
    worker: [],
    worker: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected idDocumentService: IdDocumentService,
    protected idDocumentTypeService: IdDocumentTypeService,
    protected workerService: WorkerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idDocument }) => {
      this.updateForm(idDocument);

      this.idDocumentTypeService.query().subscribe((res: HttpResponse<IIdDocumentType[]>) => (this.iddocumenttypes = res.body || []));

      this.workerService.query().subscribe((res: HttpResponse<IWorker[]>) => (this.workers = res.body || []));
    });
  }

  updateForm(idDocument: IIdDocument): void {
    this.editForm.patchValue({
      id: idDocument.id,
      idDocumentRecto: idDocument.idDocumentRecto,
      idDocumentRectoContentType: idDocument.idDocumentRectoContentType,
      idDocumentVerso: idDocument.idDocumentVerso,
      idDocumentVersoContentType: idDocument.idDocumentVersoContentType,
      type: idDocument.type,
      worker: idDocument.worker,
      worker: idDocument.worker,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ahomeApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const idDocument = this.createFromForm();
    if (idDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.idDocumentService.update(idDocument));
    } else {
      this.subscribeToSaveResponse(this.idDocumentService.create(idDocument));
    }
  }

  private createFromForm(): IIdDocument {
    return {
      ...new IdDocument(),
      id: this.editForm.get(['id'])!.value,
      idDocumentRectoContentType: this.editForm.get(['idDocumentRectoContentType'])!.value,
      idDocumentRecto: this.editForm.get(['idDocumentRecto'])!.value,
      idDocumentVersoContentType: this.editForm.get(['idDocumentVersoContentType'])!.value,
      idDocumentVerso: this.editForm.get(['idDocumentVerso'])!.value,
      type: this.editForm.get(['type'])!.value,
      worker: this.editForm.get(['worker'])!.value,
      worker: this.editForm.get(['worker'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdDocument>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

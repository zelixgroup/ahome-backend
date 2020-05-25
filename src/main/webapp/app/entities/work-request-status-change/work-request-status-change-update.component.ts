import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWorkRequestStatusChange, WorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';
import { WorkRequestStatusChangeService } from './work-request-status-change.service';
import { IWorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestService } from 'app/entities/work-request/work-request.service';

@Component({
  selector: 'jhi-work-request-status-change-update',
  templateUrl: './work-request-status-change-update.component.html',
})
export class WorkRequestStatusChangeUpdateComponent implements OnInit {
  isSaving = false;
  workrequests: IWorkRequest[] = [];

  editForm = this.fb.group({
    id: [],
    oldStatus: [],
    newStatus: [],
    dateTimeOfStatusChange: [],
    workRequest: [],
    workRequest: [],
  });

  constructor(
    protected workRequestStatusChangeService: WorkRequestStatusChangeService,
    protected workRequestService: WorkRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workRequestStatusChange }) => {
      if (!workRequestStatusChange.id) {
        const today = moment().startOf('day');
        workRequestStatusChange.dateTimeOfStatusChange = today;
      }

      this.updateForm(workRequestStatusChange);

      this.workRequestService.query().subscribe((res: HttpResponse<IWorkRequest[]>) => (this.workrequests = res.body || []));
    });
  }

  updateForm(workRequestStatusChange: IWorkRequestStatusChange): void {
    this.editForm.patchValue({
      id: workRequestStatusChange.id,
      oldStatus: workRequestStatusChange.oldStatus,
      newStatus: workRequestStatusChange.newStatus,
      dateTimeOfStatusChange: workRequestStatusChange.dateTimeOfStatusChange
        ? workRequestStatusChange.dateTimeOfStatusChange.format(DATE_TIME_FORMAT)
        : null,
      workRequest: workRequestStatusChange.workRequest,
      workRequest: workRequestStatusChange.workRequest,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workRequestStatusChange = this.createFromForm();
    if (workRequestStatusChange.id !== undefined) {
      this.subscribeToSaveResponse(this.workRequestStatusChangeService.update(workRequestStatusChange));
    } else {
      this.subscribeToSaveResponse(this.workRequestStatusChangeService.create(workRequestStatusChange));
    }
  }

  private createFromForm(): IWorkRequestStatusChange {
    return {
      ...new WorkRequestStatusChange(),
      id: this.editForm.get(['id'])!.value,
      oldStatus: this.editForm.get(['oldStatus'])!.value,
      newStatus: this.editForm.get(['newStatus'])!.value,
      dateTimeOfStatusChange: this.editForm.get(['dateTimeOfStatusChange'])!.value
        ? moment(this.editForm.get(['dateTimeOfStatusChange'])!.value, DATE_TIME_FORMAT)
        : undefined,
      workRequest: this.editForm.get(['workRequest'])!.value,
      workRequest: this.editForm.get(['workRequest'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkRequestStatusChange>>): void {
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

  trackById(index: number, item: IWorkRequest): any {
    return item.id;
  }
}

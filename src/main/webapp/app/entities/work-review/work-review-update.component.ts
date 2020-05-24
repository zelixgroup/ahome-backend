import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWorkReview, WorkReview } from 'app/shared/model/work-review.model';
import { WorkReviewService } from './work-review.service';
import { IWorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestService } from 'app/entities/work-request/work-request.service';

@Component({
  selector: 'jhi-work-review-update',
  templateUrl: './work-review-update.component.html',
})
export class WorkReviewUpdateComponent implements OnInit {
  isSaving = false;
  workrequests: IWorkRequest[] = [];

  editForm = this.fb.group({
    id: [],
    reviewDateTime: [],
    starsNumber: [],
    workRequest: [],
  });

  constructor(
    protected workReviewService: WorkReviewService,
    protected workRequestService: WorkRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workReview }) => {
      if (!workReview.id) {
        const today = moment().startOf('day');
        workReview.reviewDateTime = today;
      }

      this.updateForm(workReview);

      this.workRequestService
        .query({ filter: 'workreview-is-null' })
        .pipe(
          map((res: HttpResponse<IWorkRequest[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IWorkRequest[]) => {
          if (!workReview.workRequest || !workReview.workRequest.id) {
            this.workrequests = resBody;
          } else {
            this.workRequestService
              .find(workReview.workRequest.id)
              .pipe(
                map((subRes: HttpResponse<IWorkRequest>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IWorkRequest[]) => (this.workrequests = concatRes));
          }
        });
    });
  }

  updateForm(workReview: IWorkReview): void {
    this.editForm.patchValue({
      id: workReview.id,
      reviewDateTime: workReview.reviewDateTime ? workReview.reviewDateTime.format(DATE_TIME_FORMAT) : null,
      starsNumber: workReview.starsNumber,
      workRequest: workReview.workRequest,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workReview = this.createFromForm();
    if (workReview.id !== undefined) {
      this.subscribeToSaveResponse(this.workReviewService.update(workReview));
    } else {
      this.subscribeToSaveResponse(this.workReviewService.create(workReview));
    }
  }

  private createFromForm(): IWorkReview {
    return {
      ...new WorkReview(),
      id: this.editForm.get(['id'])!.value,
      reviewDateTime: this.editForm.get(['reviewDateTime'])!.value
        ? moment(this.editForm.get(['reviewDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      starsNumber: this.editForm.get(['starsNumber'])!.value,
      workRequest: this.editForm.get(['workRequest'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkReview>>): void {
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

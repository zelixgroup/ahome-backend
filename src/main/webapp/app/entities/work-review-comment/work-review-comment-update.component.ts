import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWorkReviewComment, WorkReviewComment } from 'app/shared/model/work-review-comment.model';
import { WorkReviewCommentService } from './work-review-comment.service';
import { IWorkReview } from 'app/shared/model/work-review.model';
import { WorkReviewService } from 'app/entities/work-review/work-review.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IWorkReview | IUser;

@Component({
  selector: 'jhi-work-review-comment-update',
  templateUrl: './work-review-comment-update.component.html',
})
export class WorkReviewCommentUpdateComponent implements OnInit {
  isSaving = false;
  workreviews: IWorkReview[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    commentDateTime: [],
    comment: [],
    workReview: [],
    user: [],
    workReview: [],
  });

  constructor(
    protected workReviewCommentService: WorkReviewCommentService,
    protected workReviewService: WorkReviewService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workReviewComment }) => {
      if (!workReviewComment.id) {
        const today = moment().startOf('day');
        workReviewComment.commentDateTime = today;
      }

      this.updateForm(workReviewComment);

      this.workReviewService.query().subscribe((res: HttpResponse<IWorkReview[]>) => (this.workreviews = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(workReviewComment: IWorkReviewComment): void {
    this.editForm.patchValue({
      id: workReviewComment.id,
      commentDateTime: workReviewComment.commentDateTime ? workReviewComment.commentDateTime.format(DATE_TIME_FORMAT) : null,
      comment: workReviewComment.comment,
      workReview: workReviewComment.workReview,
      user: workReviewComment.user,
      workReview: workReviewComment.workReview,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workReviewComment = this.createFromForm();
    if (workReviewComment.id !== undefined) {
      this.subscribeToSaveResponse(this.workReviewCommentService.update(workReviewComment));
    } else {
      this.subscribeToSaveResponse(this.workReviewCommentService.create(workReviewComment));
    }
  }

  private createFromForm(): IWorkReviewComment {
    return {
      ...new WorkReviewComment(),
      id: this.editForm.get(['id'])!.value,
      commentDateTime: this.editForm.get(['commentDateTime'])!.value
        ? moment(this.editForm.get(['commentDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comment: this.editForm.get(['comment'])!.value,
      workReview: this.editForm.get(['workReview'])!.value,
      user: this.editForm.get(['user'])!.value,
      workReview: this.editForm.get(['workReview'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkReviewComment>>): void {
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

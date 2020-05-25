import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkReviewComment } from 'app/shared/model/work-review-comment.model';

@Component({
  selector: 'jhi-work-review-comment-detail',
  templateUrl: './work-review-comment-detail.component.html',
})
export class WorkReviewCommentDetailComponent implements OnInit {
  workReviewComment: IWorkReviewComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workReviewComment }) => (this.workReviewComment = workReviewComment));
  }

  previousState(): void {
    window.history.back();
  }
}

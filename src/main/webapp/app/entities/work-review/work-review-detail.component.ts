import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkReview } from 'app/shared/model/work-review.model';

@Component({
  selector: 'jhi-work-review-detail',
  templateUrl: './work-review-detail.component.html',
})
export class WorkReviewDetailComponent implements OnInit {
  workReview: IWorkReview | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workReview }) => (this.workReview = workReview));
  }

  previousState(): void {
    window.history.back();
  }
}

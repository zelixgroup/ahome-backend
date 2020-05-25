import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';

@Component({
  selector: 'jhi-work-request-status-change-detail',
  templateUrl: './work-request-status-change-detail.component.html',
})
export class WorkRequestStatusChangeDetailComponent implements OnInit {
  workRequestStatusChange: IWorkRequestStatusChange | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workRequestStatusChange }) => (this.workRequestStatusChange = workRequestStatusChange));
  }

  previousState(): void {
    window.history.back();
  }
}

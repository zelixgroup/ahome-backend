import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWork } from 'app/shared/model/work.model';

@Component({
  selector: 'jhi-work-detail',
  templateUrl: './work-detail.component.html',
})
export class WorkDetailComponent implements OnInit {
  work: IWork | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ work }) => (this.work = work));
  }

  previousState(): void {
    window.history.back();
  }
}

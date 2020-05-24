import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkCategory } from 'app/shared/model/work-category.model';

@Component({
  selector: 'jhi-work-category-detail',
  templateUrl: './work-category-detail.component.html',
})
export class WorkCategoryDetailComponent implements OnInit {
  workCategory: IWorkCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workCategory }) => (this.workCategory = workCategory));
  }

  previousState(): void {
    window.history.back();
  }
}

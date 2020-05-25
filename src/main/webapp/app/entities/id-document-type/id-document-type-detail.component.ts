import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdDocumentType } from 'app/shared/model/id-document-type.model';

@Component({
  selector: 'jhi-id-document-type-detail',
  templateUrl: './id-document-type-detail.component.html',
})
export class IdDocumentTypeDetailComponent implements OnInit {
  idDocumentType: IIdDocumentType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idDocumentType }) => (this.idDocumentType = idDocumentType));
  }

  previousState(): void {
    window.history.back();
  }
}

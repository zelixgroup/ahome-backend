import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IIdDocument } from 'app/shared/model/id-document.model';

@Component({
  selector: 'jhi-id-document-detail',
  templateUrl: './id-document-detail.component.html',
})
export class IdDocumentDetailComponent implements OnInit {
  idDocument: IIdDocument | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idDocument }) => (this.idDocument = idDocument));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}

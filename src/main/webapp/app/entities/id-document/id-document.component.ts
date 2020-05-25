import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdDocument } from 'app/shared/model/id-document.model';
import { IdDocumentService } from './id-document.service';
import { IdDocumentDeleteDialogComponent } from './id-document-delete-dialog.component';

@Component({
  selector: 'jhi-id-document',
  templateUrl: './id-document.component.html',
})
export class IdDocumentComponent implements OnInit, OnDestroy {
  idDocuments?: IIdDocument[];
  eventSubscriber?: Subscription;

  constructor(
    protected idDocumentService: IdDocumentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.idDocumentService.query().subscribe((res: HttpResponse<IIdDocument[]>) => (this.idDocuments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIdDocuments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIdDocument): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInIdDocuments(): void {
    this.eventSubscriber = this.eventManager.subscribe('idDocumentListModification', () => this.loadAll());
  }

  delete(idDocument: IIdDocument): void {
    const modalRef = this.modalService.open(IdDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.idDocument = idDocument;
  }
}

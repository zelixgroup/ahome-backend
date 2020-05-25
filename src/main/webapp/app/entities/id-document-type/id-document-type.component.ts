import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdDocumentType } from 'app/shared/model/id-document-type.model';
import { IdDocumentTypeService } from './id-document-type.service';
import { IdDocumentTypeDeleteDialogComponent } from './id-document-type-delete-dialog.component';

@Component({
  selector: 'jhi-id-document-type',
  templateUrl: './id-document-type.component.html',
})
export class IdDocumentTypeComponent implements OnInit, OnDestroy {
  idDocumentTypes?: IIdDocumentType[];
  eventSubscriber?: Subscription;

  constructor(
    protected idDocumentTypeService: IdDocumentTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.idDocumentTypeService.query().subscribe((res: HttpResponse<IIdDocumentType[]>) => (this.idDocumentTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIdDocumentTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIdDocumentType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIdDocumentTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('idDocumentTypeListModification', () => this.loadAll());
  }

  delete(idDocumentType: IIdDocumentType): void {
    const modalRef = this.modalService.open(IdDocumentTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.idDocumentType = idDocumentType;
  }
}

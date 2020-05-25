import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AhomeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { IdDocumentDeleteDialogComponent } from 'app/entities/id-document/id-document-delete-dialog.component';
import { IdDocumentService } from 'app/entities/id-document/id-document.service';

describe('Component Tests', () => {
  describe('IdDocument Management Delete Component', () => {
    let comp: IdDocumentDeleteDialogComponent;
    let fixture: ComponentFixture<IdDocumentDeleteDialogComponent>;
    let service: IdDocumentService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentDeleteDialogComponent],
      })
        .overrideTemplate(IdDocumentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdDocumentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdDocumentService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});

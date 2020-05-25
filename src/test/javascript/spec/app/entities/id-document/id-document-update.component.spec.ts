import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { IdDocumentUpdateComponent } from 'app/entities/id-document/id-document-update.component';
import { IdDocumentService } from 'app/entities/id-document/id-document.service';
import { IdDocument } from 'app/shared/model/id-document.model';

describe('Component Tests', () => {
  describe('IdDocument Management Update Component', () => {
    let comp: IdDocumentUpdateComponent;
    let fixture: ComponentFixture<IdDocumentUpdateComponent>;
    let service: IdDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IdDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IdDocument(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new IdDocument();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

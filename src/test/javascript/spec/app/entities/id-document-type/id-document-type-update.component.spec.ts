import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { IdDocumentTypeUpdateComponent } from 'app/entities/id-document-type/id-document-type-update.component';
import { IdDocumentTypeService } from 'app/entities/id-document-type/id-document-type.service';
import { IdDocumentType } from 'app/shared/model/id-document-type.model';

describe('Component Tests', () => {
  describe('IdDocumentType Management Update Component', () => {
    let comp: IdDocumentTypeUpdateComponent;
    let fixture: ComponentFixture<IdDocumentTypeUpdateComponent>;
    let service: IdDocumentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IdDocumentTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdDocumentTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdDocumentTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IdDocumentType(123);
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
        const entity = new IdDocumentType();
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

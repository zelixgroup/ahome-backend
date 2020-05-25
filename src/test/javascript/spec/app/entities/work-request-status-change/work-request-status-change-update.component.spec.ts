import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkRequestStatusChangeUpdateComponent } from 'app/entities/work-request-status-change/work-request-status-change-update.component';
import { WorkRequestStatusChangeService } from 'app/entities/work-request-status-change/work-request-status-change.service';
import { WorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';

describe('Component Tests', () => {
  describe('WorkRequestStatusChange Management Update Component', () => {
    let comp: WorkRequestStatusChangeUpdateComponent;
    let fixture: ComponentFixture<WorkRequestStatusChangeUpdateComponent>;
    let service: WorkRequestStatusChangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkRequestStatusChangeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkRequestStatusChangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkRequestStatusChangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkRequestStatusChangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkRequestStatusChange(123);
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
        const entity = new WorkRequestStatusChange();
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

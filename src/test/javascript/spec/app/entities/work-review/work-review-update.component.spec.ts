import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkReviewUpdateComponent } from 'app/entities/work-review/work-review-update.component';
import { WorkReviewService } from 'app/entities/work-review/work-review.service';
import { WorkReview } from 'app/shared/model/work-review.model';

describe('Component Tests', () => {
  describe('WorkReview Management Update Component', () => {
    let comp: WorkReviewUpdateComponent;
    let fixture: ComponentFixture<WorkReviewUpdateComponent>;
    let service: WorkReviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkReviewUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkReviewUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkReviewUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkReviewService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkReview(123);
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
        const entity = new WorkReview();
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

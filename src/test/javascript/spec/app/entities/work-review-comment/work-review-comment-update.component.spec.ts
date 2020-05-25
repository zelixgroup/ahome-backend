import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkReviewCommentUpdateComponent } from 'app/entities/work-review-comment/work-review-comment-update.component';
import { WorkReviewCommentService } from 'app/entities/work-review-comment/work-review-comment.service';
import { WorkReviewComment } from 'app/shared/model/work-review-comment.model';

describe('Component Tests', () => {
  describe('WorkReviewComment Management Update Component', () => {
    let comp: WorkReviewCommentUpdateComponent;
    let fixture: ComponentFixture<WorkReviewCommentUpdateComponent>;
    let service: WorkReviewCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkReviewCommentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkReviewCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkReviewCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkReviewCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkReviewComment(123);
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
        const entity = new WorkReviewComment();
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

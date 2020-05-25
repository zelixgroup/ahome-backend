import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { WorkReviewCommentComponent } from 'app/entities/work-review-comment/work-review-comment.component';
import { WorkReviewCommentService } from 'app/entities/work-review-comment/work-review-comment.service';
import { WorkReviewComment } from 'app/shared/model/work-review-comment.model';

describe('Component Tests', () => {
  describe('WorkReviewComment Management Component', () => {
    let comp: WorkReviewCommentComponent;
    let fixture: ComponentFixture<WorkReviewCommentComponent>;
    let service: WorkReviewCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkReviewCommentComponent],
      })
        .overrideTemplate(WorkReviewCommentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkReviewCommentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkReviewCommentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkReviewComment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workReviewComments && comp.workReviewComments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

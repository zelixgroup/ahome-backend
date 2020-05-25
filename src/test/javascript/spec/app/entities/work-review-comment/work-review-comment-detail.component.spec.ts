import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkReviewCommentDetailComponent } from 'app/entities/work-review-comment/work-review-comment-detail.component';
import { WorkReviewComment } from 'app/shared/model/work-review-comment.model';

describe('Component Tests', () => {
  describe('WorkReviewComment Management Detail Component', () => {
    let comp: WorkReviewCommentDetailComponent;
    let fixture: ComponentFixture<WorkReviewCommentDetailComponent>;
    const route = ({ data: of({ workReviewComment: new WorkReviewComment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkReviewCommentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkReviewCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkReviewCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workReviewComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workReviewComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

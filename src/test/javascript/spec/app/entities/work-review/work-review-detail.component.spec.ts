import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkReviewDetailComponent } from 'app/entities/work-review/work-review-detail.component';
import { WorkReview } from 'app/shared/model/work-review.model';

describe('Component Tests', () => {
  describe('WorkReview Management Detail Component', () => {
    let comp: WorkReviewDetailComponent;
    let fixture: ComponentFixture<WorkReviewDetailComponent>;
    const route = ({ data: of({ workReview: new WorkReview(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkReviewDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkReviewDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkReviewDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workReview on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workReview).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

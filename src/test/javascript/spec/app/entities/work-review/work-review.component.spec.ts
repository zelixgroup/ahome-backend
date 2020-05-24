import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { WorkReviewComponent } from 'app/entities/work-review/work-review.component';
import { WorkReviewService } from 'app/entities/work-review/work-review.service';
import { WorkReview } from 'app/shared/model/work-review.model';

describe('Component Tests', () => {
  describe('WorkReview Management Component', () => {
    let comp: WorkReviewComponent;
    let fixture: ComponentFixture<WorkReviewComponent>;
    let service: WorkReviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkReviewComponent],
      })
        .overrideTemplate(WorkReviewComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkReviewComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkReviewService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkReview(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workReviews && comp.workReviews[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

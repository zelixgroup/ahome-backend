import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkRequestDetailComponent } from 'app/entities/work-request/work-request-detail.component';
import { WorkRequest } from 'app/shared/model/work-request.model';

describe('Component Tests', () => {
  describe('WorkRequest Management Detail Component', () => {
    let comp: WorkRequestDetailComponent;
    let fixture: ComponentFixture<WorkRequestDetailComponent>;
    const route = ({ data: of({ workRequest: new WorkRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workRequest on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

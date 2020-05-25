import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkRequestStatusChangeDetailComponent } from 'app/entities/work-request-status-change/work-request-status-change-detail.component';
import { WorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';

describe('Component Tests', () => {
  describe('WorkRequestStatusChange Management Detail Component', () => {
    let comp: WorkRequestStatusChangeDetailComponent;
    let fixture: ComponentFixture<WorkRequestStatusChangeDetailComponent>;
    const route = ({ data: of({ workRequestStatusChange: new WorkRequestStatusChange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkRequestStatusChangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkRequestStatusChangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkRequestStatusChangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workRequestStatusChange on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workRequestStatusChange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

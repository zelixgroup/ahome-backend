import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { WorkRequestStatusChangeComponent } from 'app/entities/work-request-status-change/work-request-status-change.component';
import { WorkRequestStatusChangeService } from 'app/entities/work-request-status-change/work-request-status-change.service';
import { WorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';

describe('Component Tests', () => {
  describe('WorkRequestStatusChange Management Component', () => {
    let comp: WorkRequestStatusChangeComponent;
    let fixture: ComponentFixture<WorkRequestStatusChangeComponent>;
    let service: WorkRequestStatusChangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkRequestStatusChangeComponent],
      })
        .overrideTemplate(WorkRequestStatusChangeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkRequestStatusChangeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkRequestStatusChangeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkRequestStatusChange(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workRequestStatusChanges && comp.workRequestStatusChanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

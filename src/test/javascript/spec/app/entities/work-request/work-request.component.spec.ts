import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { WorkRequestComponent } from 'app/entities/work-request/work-request.component';
import { WorkRequestService } from 'app/entities/work-request/work-request.service';
import { WorkRequest } from 'app/shared/model/work-request.model';

describe('Component Tests', () => {
  describe('WorkRequest Management Component', () => {
    let comp: WorkRequestComponent;
    let fixture: ComponentFixture<WorkRequestComponent>;
    let service: WorkRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkRequestComponent],
      })
        .overrideTemplate(WorkRequestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkRequestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkRequestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkRequest(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workRequests && comp.workRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

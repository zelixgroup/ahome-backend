import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkDetailComponent } from 'app/entities/work/work-detail.component';
import { Work } from 'app/shared/model/work.model';

describe('Component Tests', () => {
  describe('Work Management Detail Component', () => {
    let comp: WorkDetailComponent;
    let fixture: ComponentFixture<WorkDetailComponent>;
    const route = ({ data: of({ work: new Work(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load work on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.work).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

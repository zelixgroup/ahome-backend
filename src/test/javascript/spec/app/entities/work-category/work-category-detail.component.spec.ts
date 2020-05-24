import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkCategoryDetailComponent } from 'app/entities/work-category/work-category-detail.component';
import { WorkCategory } from 'app/shared/model/work-category.model';

describe('Component Tests', () => {
  describe('WorkCategory Management Detail Component', () => {
    let comp: WorkCategoryDetailComponent;
    let fixture: ComponentFixture<WorkCategoryDetailComponent>;
    const route = ({ data: of({ workCategory: new WorkCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

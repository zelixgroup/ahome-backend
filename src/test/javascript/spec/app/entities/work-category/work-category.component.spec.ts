import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { WorkCategoryComponent } from 'app/entities/work-category/work-category.component';
import { WorkCategoryService } from 'app/entities/work-category/work-category.service';
import { WorkCategory } from 'app/shared/model/work-category.model';

describe('Component Tests', () => {
  describe('WorkCategory Management Component', () => {
    let comp: WorkCategoryComponent;
    let fixture: ComponentFixture<WorkCategoryComponent>;
    let service: WorkCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkCategoryComponent],
      })
        .overrideTemplate(WorkCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkCategory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workCategories && comp.workCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

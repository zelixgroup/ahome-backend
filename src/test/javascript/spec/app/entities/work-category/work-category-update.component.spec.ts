import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { WorkCategoryUpdateComponent } from 'app/entities/work-category/work-category-update.component';
import { WorkCategoryService } from 'app/entities/work-category/work-category.service';
import { WorkCategory } from 'app/shared/model/work-category.model';

describe('Component Tests', () => {
  describe('WorkCategory Management Update Component', () => {
    let comp: WorkCategoryUpdateComponent;
    let fixture: ComponentFixture<WorkCategoryUpdateComponent>;
    let service: WorkCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [WorkCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkCategory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkCategory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

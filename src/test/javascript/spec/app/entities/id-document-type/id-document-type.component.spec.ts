import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { IdDocumentTypeComponent } from 'app/entities/id-document-type/id-document-type.component';
import { IdDocumentTypeService } from 'app/entities/id-document-type/id-document-type.service';
import { IdDocumentType } from 'app/shared/model/id-document-type.model';

describe('Component Tests', () => {
  describe('IdDocumentType Management Component', () => {
    let comp: IdDocumentTypeComponent;
    let fixture: ComponentFixture<IdDocumentTypeComponent>;
    let service: IdDocumentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentTypeComponent],
      })
        .overrideTemplate(IdDocumentTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdDocumentTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdDocumentTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IdDocumentType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.idDocumentTypes && comp.idDocumentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

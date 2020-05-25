import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AhomeTestModule } from '../../../test.module';
import { IdDocumentComponent } from 'app/entities/id-document/id-document.component';
import { IdDocumentService } from 'app/entities/id-document/id-document.service';
import { IdDocument } from 'app/shared/model/id-document.model';

describe('Component Tests', () => {
  describe('IdDocument Management Component', () => {
    let comp: IdDocumentComponent;
    let fixture: ComponentFixture<IdDocumentComponent>;
    let service: IdDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentComponent],
      })
        .overrideTemplate(IdDocumentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdDocumentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdDocumentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IdDocument(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.idDocuments && comp.idDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

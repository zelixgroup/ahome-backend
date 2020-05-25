import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { AhomeTestModule } from '../../../test.module';
import { IdDocumentDetailComponent } from 'app/entities/id-document/id-document-detail.component';
import { IdDocument } from 'app/shared/model/id-document.model';

describe('Component Tests', () => {
  describe('IdDocument Management Detail Component', () => {
    let comp: IdDocumentDetailComponent;
    let fixture: ComponentFixture<IdDocumentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ idDocument: new IdDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IdDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdDocumentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load idDocument on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.idDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});

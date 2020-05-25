import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AhomeTestModule } from '../../../test.module';
import { IdDocumentTypeDetailComponent } from 'app/entities/id-document-type/id-document-type-detail.component';
import { IdDocumentType } from 'app/shared/model/id-document-type.model';

describe('Component Tests', () => {
  describe('IdDocumentType Management Detail Component', () => {
    let comp: IdDocumentTypeDetailComponent;
    let fixture: ComponentFixture<IdDocumentTypeDetailComponent>;
    const route = ({ data: of({ idDocumentType: new IdDocumentType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AhomeTestModule],
        declarations: [IdDocumentTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IdDocumentTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdDocumentTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load idDocumentType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.idDocumentType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

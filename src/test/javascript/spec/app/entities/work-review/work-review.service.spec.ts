import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WorkReviewService } from 'app/entities/work-review/work-review.service';
import { IWorkReview, WorkReview } from 'app/shared/model/work-review.model';

describe('Service Tests', () => {
  describe('WorkReview Service', () => {
    let injector: TestBed;
    let service: WorkReviewService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkReview;
    let expectedResult: IWorkReview | IWorkReview[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkReviewService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new WorkReview(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            reviewDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WorkReview', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            reviewDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewDateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new WorkReview()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkReview', () => {
        const returnedFromService = Object.assign(
          {
            reviewDateTime: currentDate.format(DATE_TIME_FORMAT),
            starsNumber: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewDateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WorkReview', () => {
        const returnedFromService = Object.assign(
          {
            reviewDateTime: currentDate.format(DATE_TIME_FORMAT),
            starsNumber: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewDateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a WorkReview', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

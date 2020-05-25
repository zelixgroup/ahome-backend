import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WorkRequestService } from 'app/entities/work-request/work-request.service';
import { IWorkRequest, WorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestMagnitude } from 'app/shared/model/enumerations/work-request-magnitude.model';
import { WorkRequestStatus } from 'app/shared/model/enumerations/work-request-status.model';

describe('Service Tests', () => {
  describe('WorkRequest Service', () => {
    let injector: TestBed;
    let service: WorkRequestService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkRequest;
    let expectedResult: IWorkRequest | IWorkRequest[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkRequestService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new WorkRequest(
        0,
        currentDate,
        false,
        false,
        0,
        'AAAAAAA',
        WorkRequestMagnitude.LARGE,
        0,
        currentDate,
        currentDate,
        WorkRequestStatus.SUBMITTED
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDateTime: currentDate.format(DATE_TIME_FORMAT),
            plannedStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            plannedEndDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WorkRequest', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDateTime: currentDate.format(DATE_TIME_FORMAT),
            plannedStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            plannedEndDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDateTime: currentDate,
            plannedStartDateTime: currentDate,
            plannedEndDateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new WorkRequest()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkRequest', () => {
        const returnedFromService = Object.assign(
          {
            creationDateTime: currentDate.format(DATE_TIME_FORMAT),
            forMysef: true,
            constructionSite: true,
            mediatorPercentage: 1,
            detailedDescription: 'BBBBBB',
            magnitude: 'BBBBBB',
            estimatedWorkFees: 1,
            plannedStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            plannedEndDateTime: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDateTime: currentDate,
            plannedStartDateTime: currentDate,
            plannedEndDateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WorkRequest', () => {
        const returnedFromService = Object.assign(
          {
            creationDateTime: currentDate.format(DATE_TIME_FORMAT),
            forMysef: true,
            constructionSite: true,
            mediatorPercentage: 1,
            detailedDescription: 'BBBBBB',
            magnitude: 'BBBBBB',
            estimatedWorkFees: 1,
            plannedStartDateTime: currentDate.format(DATE_TIME_FORMAT),
            plannedEndDateTime: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDateTime: currentDate,
            plannedStartDateTime: currentDate,
            plannedEndDateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a WorkRequest', () => {
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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WorkRequestStatusChangeService } from 'app/entities/work-request-status-change/work-request-status-change.service';
import { IWorkRequestStatusChange, WorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';
import { WorkRequestStatus } from 'app/shared/model/enumerations/work-request-status.model';

describe('Service Tests', () => {
  describe('WorkRequestStatusChange Service', () => {
    let injector: TestBed;
    let service: WorkRequestStatusChangeService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkRequestStatusChange;
    let expectedResult: IWorkRequestStatusChange | IWorkRequestStatusChange[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkRequestStatusChangeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new WorkRequestStatusChange(0, WorkRequestStatus.SUBMITTED, WorkRequestStatus.SUBMITTED, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateTimeOfStatusChange: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WorkRequestStatusChange', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateTimeOfStatusChange: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTimeOfStatusChange: currentDate,
          },
          returnedFromService
        );

        service.create(new WorkRequestStatusChange()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkRequestStatusChange', () => {
        const returnedFromService = Object.assign(
          {
            oldStatus: 'BBBBBB',
            newStatus: 'BBBBBB',
            dateTimeOfStatusChange: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTimeOfStatusChange: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WorkRequestStatusChange', () => {
        const returnedFromService = Object.assign(
          {
            oldStatus: 'BBBBBB',
            newStatus: 'BBBBBB',
            dateTimeOfStatusChange: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateTimeOfStatusChange: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a WorkRequestStatusChange', () => {
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

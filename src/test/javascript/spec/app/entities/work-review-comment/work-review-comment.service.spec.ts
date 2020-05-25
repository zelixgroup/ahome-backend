import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WorkReviewCommentService } from 'app/entities/work-review-comment/work-review-comment.service';
import { IWorkReviewComment, WorkReviewComment } from 'app/shared/model/work-review-comment.model';

describe('Service Tests', () => {
  describe('WorkReviewComment Service', () => {
    let injector: TestBed;
    let service: WorkReviewCommentService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkReviewComment;
    let expectedResult: IWorkReviewComment | IWorkReviewComment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkReviewCommentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new WorkReviewComment(0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            commentDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WorkReviewComment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            commentDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            commentDateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new WorkReviewComment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkReviewComment', () => {
        const returnedFromService = Object.assign(
          {
            commentDateTime: currentDate.format(DATE_TIME_FORMAT),
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            commentDateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WorkReviewComment', () => {
        const returnedFromService = Object.assign(
          {
            commentDateTime: currentDate.format(DATE_TIME_FORMAT),
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            commentDateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a WorkReviewComment', () => {
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

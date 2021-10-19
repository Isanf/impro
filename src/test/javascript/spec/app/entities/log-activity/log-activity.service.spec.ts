import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LogActivityService } from 'app/entities/log-activity/log-activity.service';
import { ILogActivity, LogActivity } from 'app/shared/model/log-activity.model';

describe('Service Tests', () => {
  describe('LogActivity Service', () => {
    let injector: TestBed;
    let service: LogActivityService;
    let httpMock: HttpTestingController;
    let elemDefault: ILogActivity;
    let expectedResult: ILogActivity | ILogActivity[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LogActivityService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LogActivity(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAction: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LogActivity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAction: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAction: currentDate
          },
          returnedFromService
        );

        service.create(new LogActivity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LogActivity', () => {
        const returnedFromService = Object.assign(
          {
            principal: 'BBBBBB',
            url: 'BBBBBB',
            action: 'BBBBBB',
            ip: 'BBBBBB',
            dateAction: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAction: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LogActivity', () => {
        const returnedFromService = Object.assign(
          {
            principal: 'BBBBBB',
            url: 'BBBBBB',
            action: 'BBBBBB',
            ip: 'BBBBBB',
            dateAction: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAction: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LogActivity', () => {
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

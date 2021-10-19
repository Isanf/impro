import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { ICarteW, CarteW } from 'app/shared/model/carte-w.model';

describe('Service Tests', () => {
  describe('CarteW Service', () => {
    let injector: TestBed;
    let service: CarteWService;
    let httpMock: HttpTestingController;
    let elemDefault: ICarteW;
    let expectedResult: ICarteW | ICarteW[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CarteWService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CarteW(0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateEtablissementCarteW: currentDate.format(DATE_FORMAT),
            dateExpirationCarteW: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CarteW', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateEtablissementCarteW: currentDate.format(DATE_FORMAT),
            dateExpirationCarteW: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtablissementCarteW: currentDate,
            dateExpirationCarteW: currentDate
          },
          returnedFromService
        );

        service.create(new CarteW()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CarteW', () => {
        const returnedFromService = Object.assign(
          {
            numeroCarteW: 'BBBBBB',
            dateEtablissementCarteW: currentDate.format(DATE_FORMAT),
            dateExpirationCarteW: currentDate.format(DATE_FORMAT),
            lieuEtablissement: 'BBBBBB',
            codeQr: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtablissementCarteW: currentDate,
            dateExpirationCarteW: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CarteW', () => {
        const returnedFromService = Object.assign(
          {
            numeroCarteW: 'BBBBBB',
            dateEtablissementCarteW: currentDate.format(DATE_FORMAT),
            dateExpirationCarteW: currentDate.format(DATE_FORMAT),
            lieuEtablissement: 'BBBBBB',
            codeQr: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtablissementCarteW: currentDate,
            dateExpirationCarteW: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CarteW', () => {
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

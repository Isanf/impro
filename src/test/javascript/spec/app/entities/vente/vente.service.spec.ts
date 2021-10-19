import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VenteService } from 'app/entities/vente/vente.service';
import { IVente, Vente } from 'app/shared/model/vente.model';

describe('Service Tests', () => {
  describe('Vente Service', () => {
    let injector: TestBed;
    let service: VenteService;
    let httpMock: HttpTestingController;
    let elemDefault: IVente;
    let expectedResult: IVente | IVente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VenteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vente(0, currentDate, '0');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateVente: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Vente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateVente: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateVente: currentDate
          },
          returnedFromService
        );

        service.create(new Vente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Vente', () => {
        const returnedFromService = Object.assign(
          {
            numeroVente: 'BBBBBB',
            dateVente: currentDate.format(DATE_TIME_FORMAT),
            quantiteVendue: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateVente: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Vente', () => {
        const returnedFromService = Object.assign(
          {
            numeroVente: 'BBBBBB',
            dateVente: currentDate.format(DATE_TIME_FORMAT),
            quantiteVendue: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateVente: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vente', () => {
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

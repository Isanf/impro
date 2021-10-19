import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';

describe('Service Tests', () => {
  describe('Vehicule Service', () => {
    let injector: TestBed;
    let service: VehiculeService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicule;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(VehiculeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vehicule(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateMiseCirculation: currentDate.format(DATE_FORMAT),
            dateDedouanement: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Vehicule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateMiseCirculation: currentDate.format(DATE_FORMAT),
            dateDedouanement: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateMiseCirculation: currentDate,
            dateDedouanement: currentDate
          },
          returnedFromService
        );
        service
          .create(new Vehicule(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Vehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroChassis: 'BBBBBB',
            types: 'BBBBBB',
            model: 'BBBBBB',
            energie: 'BBBBBB',
            puissanceReel: 'BBBBBB',
            puissanceAdmin: 'BBBBBB',
            couleur: 'BBBBBB',
            poidsVide: 1,
            chargeUtile: 1,
            ptac: 1,
            ptra: 1,
            nbrPlace: 1,
            capacite: 1,
            dateMiseCirculation: currentDate.format(DATE_FORMAT),
            regime: 'BBBBBB',
            noDedouanement: 'BBBBBB',
            dateDedouanement: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateMiseCirculation: currentDate,
            dateDedouanement: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Vehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroChassis: 'BBBBBB',
            types: 'BBBBBB',
            model: 'BBBBBB',
            energie: 'BBBBBB',
            puissanceReel: 'BBBBBB',
            puissanceAdmin: 'BBBBBB',
            couleur: 'BBBBBB',
            poidsVide: 1,
            chargeUtile: 1,
            ptac: 1,
            ptra: 1,
            nbrPlace: 1,
            capacite: 1,
            dateMiseCirculation: currentDate.format(DATE_FORMAT),
            regime: 'BBBBBB',
            noDedouanement: 'BBBBBB',
            dateDedouanement: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateMiseCirculation: currentDate,
            dateDedouanement: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vehicule', () => {
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

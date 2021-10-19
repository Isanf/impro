import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VehiculeOccasionService } from 'app/entities/vehicule-occasion/vehicule-occasion.service';
import { IVehiculeOccasion, VehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';

describe('Service Tests', () => {
  describe('VehiculeOccasion Service', () => {
    let injector: TestBed;
    let service: VehiculeOccasionService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehiculeOccasion;
    let expectedResult: IVehiculeOccasion | IVehiculeOccasion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VehiculeOccasionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new VehiculeOccasion(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VehiculeOccasion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate
          },
          returnedFromService
        );

        service.create(new VehiculeOccasion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehiculeOccasion', () => {
        const returnedFromService = Object.assign(
          {
            chassis: 'BBBBBB',
            marque: 'BBBBBB',
            model: 'BBBBBB',
            nomPrenom: 'BBBBBB',
            telephone: 'BBBBBB',
            carteW: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            numeroCNIB: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehiculeOccasion', () => {
        const returnedFromService = Object.assign(
          {
            chassis: 'BBBBBB',
            marque: 'BBBBBB',
            model: 'BBBBBB',
            nomPrenom: 'BBBBBB',
            telephone: 'BBBBBB',
            carteW: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            numeroCNIB: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a VehiculeOccasion', () => {
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

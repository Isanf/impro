import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LivraisonVehiculeService } from 'app/entities/livraison-vehicule/livraison-vehicule.service';
import { ILivraisonVehicule, LivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

describe('Service Tests', () => {
  describe('LivraisonVehicule Service', () => {
    let injector: TestBed;
    let service: LivraisonVehiculeService;
    let httpMock: HttpTestingController;
    let elemDefault: ILivraisonVehicule;
    let expectedResult: ILivraisonVehicule | ILivraisonVehicule[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LivraisonVehiculeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LivraisonVehicule(0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateLivraison: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LivraisonVehicule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateLivraison: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateLivraison: currentDate
          },
          returnedFromService
        );

        service.create(new LivraisonVehicule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LivraisonVehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroLivraison: 'BBBBBB',
            dateLivraison: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateLivraison: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LivraisonVehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroLivraison: 'BBBBBB',
            dateLivraison: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateLivraison: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LivraisonVehicule', () => {
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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InfoCommandeVehiculeService } from 'app/entities/info-commande-vehicule/info-commande-vehicule.service';
import { IInfoCommandeVehicule, InfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

describe('Service Tests', () => {
  describe('InfoCommandeVehicule Service', () => {
    let injector: TestBed;
    let service: InfoCommandeVehiculeService;
    let httpMock: HttpTestingController;
    let elemDefault: IInfoCommandeVehicule;
    let expectedResult: IInfoCommandeVehicule | IInfoCommandeVehicule[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InfoCommandeVehiculeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InfoCommandeVehicule(0, 'AAAAAAA', currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InfoCommandeVehicule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCommande: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate
          },
          returnedFromService
        );

        service.create(new InfoCommandeVehicule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InfoCommandeVehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommande: 'BBBBBB',
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            quantiteCommande: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InfoCommandeVehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommande: 'BBBBBB',
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            quantiteCommande: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InfoCommandeVehicule', () => {
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

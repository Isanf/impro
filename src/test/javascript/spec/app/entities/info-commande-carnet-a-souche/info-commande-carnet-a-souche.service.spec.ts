import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InfoCommandeCarnetASoucheService } from 'app/entities/info-commande-carnet-a-souche/info-commande-carnet-a-souche.service';
import { IInfoCommandeCarnetASouche, InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

describe('Service Tests', () => {
  describe('InfoCommandeCarnetASouche Service', () => {
    let injector: TestBed;
    let service: InfoCommandeCarnetASoucheService;
    let httpMock: HttpTestingController;
    let elemDefault: IInfoCommandeCarnetASouche;
    let expectedResult: IInfoCommandeCarnetASouche | IInfoCommandeCarnetASouche[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InfoCommandeCarnetASoucheService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InfoCommandeCarnetASouche(0, 'AAAAAAA', currentDate, 0, false, false);
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

      it('should create a InfoCommandeCarnetASouche', () => {
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

        service.create(new InfoCommandeCarnetASouche()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InfoCommandeCarnetASouche', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommande: 'BBBBBB',
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            quantiteCommande: 1,
            estDeliver: true,
            estTransiter: true
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

      it('should return a list of InfoCommandeCarnetASouche', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommande: 'BBBBBB',
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            quantiteCommande: 1,
            estDeliver: true,
            estTransiter: true
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

      it('should delete a InfoCommandeCarnetASouche', () => {
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

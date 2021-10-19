import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';
import { ILivraisonCarnetSouche, LivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

describe('Service Tests', () => {
  describe('LivraisonCarnetSouche Service', () => {
    let injector: TestBed;
    let service: LivraisonCarnetSoucheService;
    let httpMock: HttpTestingController;
    let elemDefault: ILivraisonCarnetSouche;
    let expectedResult: ILivraisonCarnetSouche | ILivraisonCarnetSouche[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LivraisonCarnetSoucheService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LivraisonCarnetSouche(0, 'AAAAAAA', currentDate);
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

      it('should create a LivraisonCarnetSouche', () => {
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

        service.create(new LivraisonCarnetSouche()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LivraisonCarnetSouche', () => {
        const returnedFromService = Object.assign(
          {
            numeroLivraisonCS: 'BBBBBB',
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

      it('should return a list of LivraisonCarnetSouche', () => {
        const returnedFromService = Object.assign(
          {
            numeroLivraisonCS: 'BBBBBB',
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

      it('should delete a LivraisonCarnetSouche', () => {
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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeCarnetSoucheService } from 'app/entities/commande-carnet-souche/commande-carnet-souche.service';
import { ICommandeCarnetSouche, CommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

describe('Service Tests', () => {
  describe('CommandeCarnetSouche Service', () => {
    let injector: TestBed;
    let service: CommandeCarnetSoucheService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommandeCarnetSouche;
    let expectedResult: ICommandeCarnetSouche | ICommandeCarnetSouche[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeCarnetSoucheService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommandeCarnetSouche(0, 'AAAAAAA', currentDate, '', false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCommandeCS: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommandeCarnetSouche', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCommandeCS: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommandeCS: currentDate
          },
          returnedFromService
        );

        service.create(new CommandeCarnetSouche()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommandeCarnetSouche', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommandeCS: 'BBBBBB',
            dateCommandeCS: currentDate.format(DATE_TIME_FORMAT),
            estValide: true,
            estTraitee: true,
            estLivree: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommandeCS: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommandeCarnetSouche', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommandeCS: 'BBBBBB',
            dateCommandeCS: currentDate.format(DATE_TIME_FORMAT),
            estValide: true,
            estTraitee: true,
            estLivree: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommandeCS: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommandeCarnetSouche', () => {
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

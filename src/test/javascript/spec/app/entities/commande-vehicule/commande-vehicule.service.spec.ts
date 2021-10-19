import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeVehiculeService } from 'app/entities/commande-vehicule/commande-vehicule.service';
import { ICommandeVehicule, CommandeVehicule } from 'app/shared/model/commande-vehicule.model';

describe('Service Tests', () => {
  describe('CommandeVehicule Service', () => {
    let injector: TestBed;
    let service: CommandeVehiculeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommandeVehicule;
    let expectedResult: ICommandeVehicule | ICommandeVehicule[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeVehiculeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommandeVehicule(0, 'AAAAAAA', currentDate, false);
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

      it('should create a CommandeVehicule', () => {
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

        service.create(new CommandeVehicule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommandeVehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommandeVehicule: 'BBBBBB',
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            estLivree: true
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

      it('should return a list of CommandeVehicule', () => {
        const returnedFromService = Object.assign(
          {
            numeroCommandeVehicule: 'BBBBBB',
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            estLivree: true
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

      it('should delete a CommandeVehicule', () => {
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

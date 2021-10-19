import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PlaqueImmatriculationService } from 'app/entities/plaque-immatriculation/plaque-immatriculation.service';
import { IPlaqueImmatriculation, PlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

describe('Service Tests', () => {
  describe('PlaqueImmatriculation Service', () => {
    let injector: TestBed;
    let service: PlaqueImmatriculationService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlaqueImmatriculation;
    let expectedResult: IPlaqueImmatriculation | IPlaqueImmatriculation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlaqueImmatriculationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PlaqueImmatriculation(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PlaqueImmatriculation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PlaqueImmatriculation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PlaqueImmatriculation', () => {
        const returnedFromService = Object.assign(
          {
            numeroSerie: 'BBBBBB',
            numeroImmatriculation: 'BBBBBB',
            codeQR: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PlaqueImmatriculation', () => {
        const returnedFromService = Object.assign(
          {
            numeroSerie: 'BBBBBB',
            numeroImmatriculation: 'BBBBBB',
            codeQR: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PlaqueImmatriculation', () => {
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

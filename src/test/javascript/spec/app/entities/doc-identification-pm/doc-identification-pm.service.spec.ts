import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DocIdentificationPMService } from 'app/entities/doc-identification-pm/doc-identification-pm.service';
import { IDocIdentificationPM, DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';

describe('Service Tests', () => {
  describe('DocIdentificationPM Service', () => {
    let injector: TestBed;
    let service: DocIdentificationPMService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocIdentificationPM;
    let expectedResult: IDocIdentificationPM | IDocIdentificationPM[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocIdentificationPMService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DocIdentificationPM(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DocIdentificationPM', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DocIdentificationPM()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DocIdentificationPM', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            numeroIFU: 'BBBBBB',
            numeroRCCM: 'BBBBBB',
            telephone: 'BBBBBB',
            siegeSocial: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DocIdentificationPM', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            numeroIFU: 'BBBBBB',
            numeroRCCM: 'BBBBBB',
            telephone: 'BBBBBB',
            siegeSocial: 'BBBBBB'
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

      it('should delete a DocIdentificationPM', () => {
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

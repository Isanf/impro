import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DocIdentificationPPService } from 'app/entities/doc-identification-pp/doc-identification-pp.service';
import { IDocIdentificationPP, DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { TypeDocIdentification } from 'app/shared/model/enumerations/type-doc-identification.model';

describe('Service Tests', () => {
  describe('DocIdentificationPP Service', () => {
    let injector: TestBed;
    let service: DocIdentificationPPService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocIdentificationPP;
    let expectedResult: IDocIdentificationPP | IDocIdentificationPP[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocIdentificationPPService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DocIdentificationPP(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', TypeDocIdentification.CNIB);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateEtablissement: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DocIdentificationPP', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateEtablissement: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtablissement: currentDate
          },
          returnedFromService
        );

        service.create(new DocIdentificationPP()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DocIdentificationPP', () => {
        const returnedFromService = Object.assign(
          {
            numeroDoc: 'BBBBBB',
            nip: 'BBBBBB',
            dateEtablissement: currentDate.format(DATE_FORMAT),
            lieuEtablissement: 'BBBBBB',
            autoriteEmettrice: 'BBBBBB',
            typeDocIdentification: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtablissement: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DocIdentificationPP', () => {
        const returnedFromService = Object.assign(
          {
            numeroDoc: 'BBBBBB',
            nip: 'BBBBBB',
            dateEtablissement: currentDate.format(DATE_FORMAT),
            lieuEtablissement: 'BBBBBB',
            autoriteEmettrice: 'BBBBBB',
            typeDocIdentification: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtablissement: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DocIdentificationPP', () => {
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

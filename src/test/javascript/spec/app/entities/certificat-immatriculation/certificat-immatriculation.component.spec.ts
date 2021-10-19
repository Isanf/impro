import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { CertificatImmatriculationComponent } from 'app/entities/certificat-immatriculation/certificat-immatriculation.component';
import { CertificatImmatriculationService } from 'app/entities/certificat-immatriculation/certificat-immatriculation.service';
import { CertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';

describe('Component Tests', () => {
  describe('CertificatImmatriculation Management Component', () => {
    let comp: CertificatImmatriculationComponent;
    let fixture: ComponentFixture<CertificatImmatriculationComponent>;
    let service: CertificatImmatriculationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CertificatImmatriculationComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(CertificatImmatriculationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CertificatImmatriculationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CertificatImmatriculationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CertificatImmatriculation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.certificatImmatriculations && comp.certificatImmatriculations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CertificatImmatriculation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.certificatImmatriculations && comp.certificatImmatriculations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});

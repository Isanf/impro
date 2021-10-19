import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CertificatImmatriculationDetailComponent } from 'app/entities/certificat-immatriculation/certificat-immatriculation-detail.component';
import { CertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';

describe('Component Tests', () => {
  describe('CertificatImmatriculation Management Detail Component', () => {
    let comp: CertificatImmatriculationDetailComponent;
    let fixture: ComponentFixture<CertificatImmatriculationDetailComponent>;
    const route = ({ data: of({ certificatImmatriculation: new CertificatImmatriculation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CertificatImmatriculationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CertificatImmatriculationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CertificatImmatriculationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load certificatImmatriculation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.certificatImmatriculation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

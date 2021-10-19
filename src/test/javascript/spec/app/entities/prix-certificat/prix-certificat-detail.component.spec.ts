import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PrixCertificatDetailComponent } from 'app/entities/prix-certificat/prix-certificat-detail.component';
import { PrixCertificat } from 'app/shared/model/prix-certificat.model';

describe('Component Tests', () => {
  describe('PrixCertificat Management Detail Component', () => {
    let comp: PrixCertificatDetailComponent;
    let fixture: ComponentFixture<PrixCertificatDetailComponent>;
    const route = ({ data: of({ prixCertificat: new PrixCertificat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PrixCertificatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrixCertificatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrixCertificatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prixCertificat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prixCertificat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

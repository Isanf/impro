import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { ProfilDetailComponent } from 'app/entities/profil/profil-detail.component';
import { Profil } from 'app/shared/model/profil.model';

describe('Component Tests', () => {
  describe('Profil Management Detail Component', () => {
    let comp: ProfilDetailComponent;
    let fixture: ComponentFixture<ProfilDetailComponent>;
    const route = ({ data: of({ profil: new Profil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [ProfilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

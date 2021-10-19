import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { StatistiqueDetailComponent } from 'app/entities/statistique/statistique-detail.component';
import { Statistique } from 'app/shared/model/statistique.model';

describe('Component Tests', () => {
  describe('Statistique Management Detail Component', () => {
    let comp: StatistiqueDetailComponent;
    let fixture: ComponentFixture<StatistiqueDetailComponent>;
    const route = ({ data: of({ statistique: new Statistique(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [StatistiqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StatistiqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatistiqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statistique on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statistique).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

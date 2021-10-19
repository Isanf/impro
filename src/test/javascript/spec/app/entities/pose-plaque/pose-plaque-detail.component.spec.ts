import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PosePlaqueDetailComponent } from 'app/entities/pose-plaque/pose-plaque-detail.component';
import { PosePlaque } from 'app/shared/model/pose-plaque.model';

describe('Component Tests', () => {
  describe('PosePlaque Management Detail Component', () => {
    let comp: PosePlaqueDetailComponent;
    let fixture: ComponentFixture<PosePlaqueDetailComponent>;
    const route = ({ data: of({ posePlaque: new PosePlaque(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PosePlaqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PosePlaqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PosePlaqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.posePlaque).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

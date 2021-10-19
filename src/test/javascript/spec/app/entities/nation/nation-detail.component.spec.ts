import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { NationDetailComponent } from 'app/entities/nation/nation-detail.component';
import { Nation } from 'app/shared/model/nation.model';

describe('Component Tests', () => {
  describe('Nation Management Detail Component', () => {
    let comp: NationDetailComponent;
    let fixture: ComponentFixture<NationDetailComponent>;
    const route = ({ data: of({ nation: new Nation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [NationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

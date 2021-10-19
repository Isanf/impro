import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeCarnetDetailComponent } from 'app/entities/type-carnet/type-carnet-detail.component';
import { TypeCarnet } from 'app/shared/model/type-carnet.model';

describe('Component Tests', () => {
  describe('TypeCarnet Management Detail Component', () => {
    let comp: TypeCarnetDetailComponent;
    let fixture: ComponentFixture<TypeCarnetDetailComponent>;
    const route = ({ data: of({ typeCarnet: new TypeCarnet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeCarnetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeCarnetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeCarnetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeCarnet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeCarnet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

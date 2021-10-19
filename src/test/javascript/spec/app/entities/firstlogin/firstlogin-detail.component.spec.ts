import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { FirstloginDetailComponent } from 'app/entities/firstlogin/firstlogin-detail.component';
import { Firstlogin } from 'app/shared/model/firstlogin.model';

describe('Component Tests', () => {
  describe('Firstlogin Management Detail Component', () => {
    let comp: FirstloginDetailComponent;
    let fixture: ComponentFixture<FirstloginDetailComponent>;
    const route = ({ data: of({ firstlogin: new Firstlogin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [FirstloginDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FirstloginDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FirstloginDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load firstlogin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.firstlogin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

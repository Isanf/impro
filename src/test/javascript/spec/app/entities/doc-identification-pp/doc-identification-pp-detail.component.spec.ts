import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { DocIdentificationPPDetailComponent } from 'app/entities/doc-identification-pp/doc-identification-pp-detail.component';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

describe('Component Tests', () => {
  describe('DocIdentificationPP Management Detail Component', () => {
    let comp: DocIdentificationPPDetailComponent;
    let fixture: ComponentFixture<DocIdentificationPPDetailComponent>;
    const route = ({ data: of({ docIdentificationPP: new DocIdentificationPP(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [DocIdentificationPPDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocIdentificationPPDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocIdentificationPPDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load docIdentificationPP on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.docIdentificationPP).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
